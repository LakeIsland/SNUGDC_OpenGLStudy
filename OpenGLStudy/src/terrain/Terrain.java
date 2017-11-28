package terrain;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import camera.Camera;
import texture.TerrainTexture;
import texture.Texture;
import texture.TextureLoader;

public class Terrain {
	private GeoMipMapPatch[][] mapBlocks;
	private int[][] lodTables;
	
	private final Texture terrainTexture;
	private final Texture terrainHeightMapTexture;
	
	private final boolean hasBlendMap;
	private Texture blendMap;
	private TerrainTexturePackage terrainTexturePackage;
	
	private final GeoMipMapPatchMesh geoMipMapPatchMesh;
	private final short[] rawHeightInfo;
	
	public Terrain(String terrainName){
		
		MapConstants.initFromXML(terrainName);
		
		hasBlendMap = MapConstants.HAS_BLEND_MAP;
		
		geoMipMapPatchMesh = new GeoMipMapPatchMesh(MapConstants.MAP_BLOCK_SIZE, MapConstants.MAX_LOD_LEVEL);
		terrainTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_TEXTURE_FILE);
		
		rawHeightInfo = new short[ MapConstants.MAP_SIZE *  MapConstants.MAP_SIZE];
		ShortBuffer sb = null;
		
		if(MapConstants.MAP_HEIGHT_FILE.endsWith(".png")){
			IntBuffer w = BufferUtils.createIntBuffer(1);
			IntBuffer h = BufferUtils.createIntBuffer(1);
			IntBuffer comp = BufferUtils.createIntBuffer(1);
			
			sb = STBImage.stbi_load_16(MapConstants.MAP_HEIGHT_FILE, w, h, comp, 1);
			
		}
		else if(MapConstants.MAP_HEIGHT_FILE.endsWith(".raw")){
			byte[] binaryData = null;
			try {
				binaryData = Files.readAllBytes(Paths.get(MapConstants.MAP_HEIGHT_FILE));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteBuffer byteBuffer = ByteBuffer.wrap(binaryData);
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
			sb = byteBuffer.asShortBuffer();
		}
		sb.get(rawHeightInfo);
		
		//sb.flip();
		terrainHeightMapTexture = new TerrainTexture(MapConstants.MAP_SIZE, MapConstants.MAP_SIZE, rawHeightInfo);
		MemoryUtil.memFree(sb);
		
		//terrainHeightMapTexture = TextureLoader.getNormalGray16Texture(MapConstants.MAP_HEIGHT_FILE);
		
		if(hasBlendMap){
			blendMap = TextureLoader.getNormalRGBTexture(MapConstants.MAP_BLENDMAP);
			Texture rTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_RED_TEXTURE);
			Texture gTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_GREEN_TEXTURE);
			Texture bTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_BLUE_TEXTURE);
			Texture backgroundTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_BACKGROUND_TEXTURE);
			terrainTexturePackage = new TerrainTexturePackage(rTexture, gTexture, bTexture, backgroundTexture);
		}
		
		mapBlocks = new GeoMipMapPatch[MapConstants.MAP_BLOCK_NUMBER][MapConstants.MAP_BLOCK_NUMBER];
		lodTables = new int[MapConstants.MAP_BLOCK_NUMBER][MapConstants.MAP_BLOCK_NUMBER];
		
		for (int i = 0; i < MapConstants.MAP_BLOCK_NUMBER; i++) {
			for (int j = 0; j < MapConstants.MAP_BLOCK_NUMBER; j++) {
				GeoMipMapPatch gmmp = new GeoMipMapPatch(i, j);
				mapBlocks[i][j] = gmmp;
			}
		}
	}
	
	public void updateLODLevel(Camera camera){
		for (int i = 0; i < MapConstants.MAP_BLOCK_NUMBER; i++) {
			for (int j = 0; j < MapConstants.MAP_BLOCK_NUMBER; j++) {
				lodTables[i][j] = mapBlocks[i][j].calculateLodLevel(camera);
			}
		}
	}

	public GeoMipMapPatch[][] getMapBlocks() {
		return mapBlocks;
	}

	public int[][] getLodTables() {
		return lodTables;
	}

	public TerrainTexturePackage getTerrainTexturePackage() {
		return terrainTexturePackage;
	}
	
	public Texture getBlendMap(){
		return blendMap;
	}
	
	public Texture getTerrainTexture(){
		return terrainTexture;
	}
	
	public Texture getTerrainHeightMapTexture(){
		return terrainHeightMapTexture;
	}

	public GeoMipMapPatchMesh getGeoMipMapPatchMesh() {
		return geoMipMapPatchMesh;
	}
	
	public float getHeight(int globalX, int globalY){
		
		if (globalX >= MapConstants.MAP_SIZE)
			globalX = MapConstants.MAP_SIZE - 1;
		if (globalX < 0)
			globalX = 0;
		if (globalY >= MapConstants.MAP_SIZE)
			globalY = MapConstants.MAP_SIZE - 1;
		if (globalY < 0)
			globalY = 0;
		
		short i = rawHeightInfo[globalY * MapConstants.MAP_SIZE + globalX];
		int t = Short.toUnsignedInt(i);
		
		return MapConstants.MAP_MIN_HEIGHT + MapConstants.MAP_INCREMENT_PER_BIT * t;
		
	}
	
	public float getExactHeight(float globalX, float globalY){
		float scaledX = globalX / MapConstants.MAP_SCALE;
		float scaledZ = globalY / MapConstants.MAP_SCALE;
		
		int gridX = (int)scaledX;
		int gridZ = (int)scaledZ;
		
		float xCoord = scaledX - gridX;
		float zCoord = scaledZ - gridZ;
		float height;
		
		// TODO : calculate height.
		// use barycentric.
		
		if (xCoord <= (1 - zCoord)) {
			height = xCoord * getHeight(gridX + 1, gridZ) + zCoord * getHeight(gridX, gridZ + 1)
			+ (1 - xCoord - zCoord) * getHeight(gridX, gridZ);
		} else {
			height = (1-xCoord) * getHeight(gridX, gridZ + 1) + (1-zCoord) * getHeight(gridX + 1, gridZ)
			+ (-1 + xCoord + zCoord) * getHeight(gridX + 1, gridZ + 1);
		}

		return MapConstants.MAP_SCALE * height;
		
	}

}
