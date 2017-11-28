package util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import resourceLoader.OBJData;

public class Loader {
	
	public static int createVBO(float[] data) {
		// VBO �����
		int VBO = GL15.glGenBuffers();

		// bind ��Ű��.
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);

		// ������ �ֱ�.
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);

		// unbind.
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		return VBO;
	}
	
	public static int createVAO(float[] vertices, float[] colors) {
		// VAO �����
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		// ������ attrib index�� VBO �Ҵ��ϱ�.
		storeDataInAttributeList(0, 3, vertices);
		storeDataInAttributeList(1, 3, colors);
		
		// unbind.
		GL30.glBindVertexArray(0);
		return vaoID;
	}
	
	public static int loadPos(float[] positions, int size) {
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		storeDataInAttributeList(0, size, positions);
		
		GL30.glBindVertexArray(0);
		return vaoID;
	}
	
	public static int loadPosColor(float[] positions,float[] colors, int[] indices) {
		// VAO �����
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		bindIndicesBuffer(indices);
		
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 3, colors);

		// unbind.
		GL30.glBindVertexArray(0);
		return vaoID;
	}
	
	public static int loadPosColorTex(float[] positions, float[] colors,float[] textureCoords, int[] indices) {
		// VAO �����
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		bindIndicesBuffer(indices);
		
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 3, colors);
		storeDataInAttributeList(2, 2, textureCoords);

		// unbind.
		GL30.glBindVertexArray(0);
		return vaoID;
	}
	
	public static RawModel loadRawModelWithPos(float[] positions, int size) {
		// VAO �����
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		storeDataInAttributeList(0, size, positions);

		// unbind.
		GL30.glBindVertexArray(0);
		return new RawModel(vaoID, positions.length);
	}
	
	public static RawModel loadPosTex(float[] positions,float[] textureCoords, int[] indices) {
		// VAO �����
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		bindIndicesBuffer(indices);
		
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);

		// unbind.
		GL30.glBindVertexArray(0);
		return new RawModel(vaoID, indices.length);
	}
	
	public static RawModel loadPosTexNorm(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		// VAO �����
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		bindIndicesBuffer(indices);
		
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);

		// unbind.
		GL30.glBindVertexArray(0);
		
		return new RawModel(vaoID, indices.length);
	}
	
	private static int storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		// vbo �����.
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		// ����Ÿ �ִ´�.
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		// �ش� index Ȱ��ȭ �����ְ� VBO ����Ű�� �Ѵ�.
		GL20.glEnableVertexAttribArray(attributeNumber);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		
		// unbind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}
	
	public static int bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		return vboID;
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}
