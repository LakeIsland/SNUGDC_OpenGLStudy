package terrain;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import shader.Shader;

public class TerrainRenderer {
	private Shader shader;
	
	public TerrainRenderer(){
		String VERTEX_SHADER = "res/shaders/terrain/terrainVertexShader.txt";
		String FRAGMENT_SHADER = "res/shaders/terrain/terrainFragmentShader.txt";
		
		shader = new Shader(VERTEX_SHADER, FRAGMENT_SHADER);
		
		shader.bindAttribute(0, "position");
		
		shader.start();
		
		shader.setFloat("scale", MapConstants.MAP_SCALE);
		shader.setFloat("heightScale", MapConstants.HEIGHT_SCALE);
		
		shader.setFloat("map_size", (float)MapConstants.MAP_SIZE);
		shader.setFloat("MIN_HEIGHT", MapConstants.MAP_MIN_HEIGHT);
		shader.setFloat("RANGE", MapConstants.MAP_HEIGHT_RANGE);
		
		shader.setInteger("totalMapTexture", 0);
		shader.setInteger("heightMap", 1);
		
		shader.setInteger("blendMap", 2);
		shader.setInteger("backgroundTexture", 3);
		shader.setInteger("rTexture", 4);
		shader.setInteger("gTexture", 5);
		shader.setInteger("bTexture", 6);
		
		shader.stop();
		
	}
	private int renderedTris = 0;
	
	public void render(Terrain terrain, Camera camera, Matrix4f viewMatrix, Matrix4f projectionMatrix){
		shader.start();

		shader.setMatrix4f("projectionMatrix", projectionMatrix);
		shader.setMatrix4f("viewMatrix", viewMatrix);
		
		shader.setVector3f("cameraPos", camera.getPosition());
		prepareTerrainTexture(terrain);
		
		GeoMipMapPatchMesh patchMesh = terrain.getGeoMipMapPatchMesh();
		
		GL30.glBindVertexArray(patchMesh.getVaoID());
		//GL20.glEnableVertexAttribArray(0);
		
		renderedTris = 0;
		for (int i = 0; i < MapConstants.MAP_BLOCK_NUMBER; i++) {
			for (int j = 0; j < MapConstants.MAP_BLOCK_NUMBER; j++) {
				shader.setVector2f("tileOffset", new Vector2f(i* MapConstants.MAP_BLOCK_SIZE,j* MapConstants.MAP_BLOCK_SIZE));
				renderij(terrain, patchMesh, i , j );
			}
		}
		
		//System.out.println("Tri rendered: " + renderedTris);
		
		GL30.glBindVertexArray(0);
		//GL20.glDisableVertexAttribArray(0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		shader.stop();
	}
	
	private void prepareTerrainTexture(Terrain terrain){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		terrain.getTerrainTexture().bind();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		terrain.getTerrainHeightMapTexture().bind();
		
		if(MapConstants.HAS_BLEND_MAP){
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			terrain.getBlendMap().bind();
			prepareTerrainTexturePackages(terrain.getTerrainTexturePackage());
		}
	}
	
	private void prepareTerrainTexturePackages(TerrainTexturePackage texturePackage){
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		texturePackage.getBackgroundTexture().bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		texturePackage.getRedChannelTexture().bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		texturePackage.getGreenChannelTexture().bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE6);
		texturePackage.getBlueChannelTexture().bind();
	}
	
	private void renderij(Terrain terrain, GeoMipMapPatchMesh mesh, int i, int j){
		
		int[][] lodTables = terrain.getLodTables();
		
		int LOD = lodTables[i][j];

		// most coarse
		if(LOD == MapConstants.MAX_LOD_LEVEL){
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getMostCoarseIbo());
			GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getMostCoarseIbo(), GL11.GL_UNSIGNED_INT, 0);
			renderedTris += mesh.getMostCoarseIbo();
			return;
		}
		
		//int lodCount;
		
		int[][] iboLengths = mesh.getIboLengths();
		int[][] iboIDs = mesh.getIboIDs();
		
		// center 
		int indiceCount = iboLengths[LOD][2];
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIboIDs()[LOD][0]);
		GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
		renderedTris += indiceCount;
		
		// left
		if(i>0 && lodTables[i-1][j] > LOD ){
			indiceCount = iboLengths[LOD][0];
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,mesh.getIboIDs()[LOD][4]);
			GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
		}
		else{
			indiceCount = iboLengths[LOD][1];
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboIDs[LOD][3]);
			GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
		}
		renderedTris += indiceCount;
		
		
		//right
		if(i<MapConstants.MAP_BLOCK_NUMBER - 1 && lodTables[i+1][j] > LOD){
			indiceCount = iboLengths[LOD][0];
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboIDs[LOD][8]);
			GL11.glDrawElements(GL11.GL_TRIANGLES,indiceCount, GL11.GL_UNSIGNED_INT, 0);
		}
		else{
			indiceCount = iboLengths[LOD][1];
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboIDs[LOD][7]);
			GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
		}
		renderedTris += indiceCount;
		
		//top
		if(j>0 && lodTables[i][j-1] > LOD){
			indiceCount = iboLengths[LOD][0];
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboIDs[LOD][2]);
			GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
		}
		else{
			indiceCount = iboLengths[LOD][1];
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboIDs[LOD][1]);
			GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
		}
		renderedTris += indiceCount;
		
		
		//bottom
		if(j < MapConstants.MAP_BLOCK_NUMBER - 1 && lodTables[i][j+1] > LOD){
			indiceCount = iboLengths[LOD][0];
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboIDs[LOD][6]);
			GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
		}
		else{
			indiceCount = iboLengths[LOD][1];
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboIDs[LOD][5]);
			GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
		}
		renderedTris += indiceCount;
	}
}
