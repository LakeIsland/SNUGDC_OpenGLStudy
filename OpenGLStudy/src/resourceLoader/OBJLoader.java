package resourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import models.RawModel;
import util.Loader;

public class OBJLoader {
	
	public static int orgVerticesCount;

	public static RawModel loadOBJModel(String fileName){
		OBJData objData = loadRawModelData(fileName);
		return Loader.loadPosTexNorm(objData.getVerticesArray(), objData.getTextureArray(), objData.getNormalsArray(), objData.getIndicesArray());
	}
	
	private static OBJData loadRawModelData(String fileName){
		
		FileReader fr = null;
		try {
			fr = new FileReader(new File(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load file!");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();

		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		
		VertexData[] vertexDatas = null;
		
		try {
			while (true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) {
					float x = Float.parseFloat(currentLine[1]);
					float y = Float.parseFloat(currentLine[2]);
					float z = Float.parseFloat(currentLine[3]);

					Vector3f vertex = new Vector3f(x, y, z);
					vertices.add(vertex);

				} else if (line.startsWith("vt ")) {
					float x = Float.parseFloat(currentLine[1]);
					float y = Float.parseFloat(currentLine[2]);

					Vector2f texture = new Vector2f(x, y);
					textures.add(texture);

				} else if (line.startsWith("vn ")) {
					float x = Float.parseFloat(currentLine[1]);
					float y = Float.parseFloat(currentLine[2]);
					float z = Float.parseFloat(currentLine[3]);

					Vector3f normal = new Vector3f(x, y, z);
					normals.add(normal);

				} else if (line.startsWith("f ")) {
					
					orgVerticesCount = vertices.size();
					//VertexData.setNormalsAndTextureData(normalsArray, textureArray);
					
					vertexDatas = VertexData.getNewVertexDataSet(orgVerticesCount);
					break;
				}
			}
			
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, vertexDatas,indices);
				processVertex(vertex2, vertexDatas,indices);
				processVertex(vertex3, vertexDatas,indices);
				
				line = reader.readLine();
			}
			
			reader.close();

		} catch (IOException e) {
			System.out.println(fileName + "OBJ LOAD FAILED");
			e.printStackTrace();
		}
		
		verticesArray = new float[VertexData.totalVerticesCount * 3];
		normalsArray = new float[VertexData.totalVerticesCount * 3];
		textureArray = new float[VertexData.totalVerticesCount * 2];
		
		indicesArray = new int[indices.size()];
		
		for(int i=0; i< orgVerticesCount; i++){
			Vector3f currentVertex = vertices.get(i);
			VertexData vd = vertexDatas[i];
			
			for(int j=0; j<vd.vertexIndices.size(); j++){
				
				int vertexIndex = vd.vertexIndices.get(j);
				
				Vector3f currentNormal = normals.get(vd.normalIndices.get(j));
				Vector2f currentTexture = textures.get(vd.textureIndices.get(j));
				
				verticesArray[3 * vertexIndex] = currentVertex.x;
				verticesArray[3 * vertexIndex + 1] = currentVertex.y;
				verticesArray[3 * vertexIndex + 2] = currentVertex.z;
					
				normalsArray[3 * vertexIndex] = currentNormal.x;
				normalsArray[3 * vertexIndex + 1] = currentNormal.y;
				normalsArray[3 * vertexIndex + 2] = currentNormal.z;
				
				textureArray[2 * vertexIndex] = currentTexture.x;
				textureArray[2 * vertexIndex + 1] = 1 - currentTexture.y;
			}
		}
			
		
		for(int i=0; i<indices.size(); i++){
			indicesArray[i] = indices.get(i);
		}
		//long endtime = System.currentTimeMillis();
		//System.out.println(endtime -startTime);
		//System.out.println(fileName);
		
		OBJData rmd = new OBJData(verticesArray, normalsArray, textureArray, indicesArray);
		return rmd;
	}

	private static void processVertex(String[] vertexData, VertexData[] vertexDatas, List<Integer> indices) {
		
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		
		int newVertexIndex = vertexDatas[currentVertexPointer].addNormalTexture(Integer.parseInt(vertexData[2]) - 1, Integer.parseInt(vertexData[1]) - 1);
		
		indices.add(newVertexIndex);
		
	}
	
	
}
