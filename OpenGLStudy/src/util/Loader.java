package util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

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
	
	private static int storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		// vbo �����.
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		// ����Ÿ �ִ´�.
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		
		// �ش� index Ȱ��ȭ �����ְ� VBO ����Ű�� �Ѵ�.
		GL20.glEnableVertexAttribArray(attributeNumber);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		
		// unbind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}
	
}
