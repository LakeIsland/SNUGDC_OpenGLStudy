package resourceLoader;

import java.util.ArrayList;
import java.util.List;

public class VertexData{
	
	public static int totalVerticesCount;
	
	public List<Integer> normalIndices;
	public List<Integer> textureIndices;
	public List<Integer> vertexIndices;
	
	public int vertexIndex;
	
	public static VertexData[] getNewVertexDataSet(int number){
		
		totalVerticesCount = 0;
		VertexData[] vertexDatas = new VertexData[number];
		for(int i=0; i< number; i++)
			vertexDatas[i] = new VertexData(i);
		return vertexDatas;
	}
	
	private VertexData(int vertexIndex){
		this.vertexIndex = vertexIndex;
		normalIndices = new ArrayList<Integer>();
		textureIndices = new ArrayList<Integer>();
		vertexIndices = new ArrayList<Integer>();
	}
	
	
	public int addNormalTexture(int normalIndex, int textureIndex){
		
		if(!vertexIndices.isEmpty()){
			for(int i=0; i<vertexIndices.size(); i++){
				int ni = normalIndices.get(i);
				int ti = textureIndices.get(i);
				
				if(ni == normalIndex && ti == textureIndex){
					return vertexIndices.get(i);
				}
			}
		}
		
		normalIndices.add(normalIndex);
		textureIndices.add(textureIndex);
		vertexIndices.add(totalVerticesCount);
		
		totalVerticesCount += 1;
		return totalVerticesCount - 1;
	}
}
