package resourceLoader;

public class OBJData {
	private final float[] verticesArray;
	private final float[] normalsArray;
	private final float[] textureArray;
	private final int[] indicesArray;
	
	public OBJData(float[] verticesArray, float[] normalsArray, float[] textureArray, int[] indicesArray) {
		this.verticesArray = verticesArray;
		this.normalsArray = normalsArray;
		this.textureArray = textureArray;
		this.indicesArray = indicesArray;	
	}

	public float[] getVerticesArray() {
		return verticesArray;
	}

	public float[] getNormalsArray() {
		return normalsArray;
	}

	public float[] getTextureArray() {
		return textureArray;
	}

	public int[] getIndicesArray() {
		return indicesArray;
	}

}
