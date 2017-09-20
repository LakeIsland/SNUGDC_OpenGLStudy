package util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {
	public static int createVBO(float[] data) {
		// VBO 만들기
		int VBO = GL15.glGenBuffers();

		// bind 시키기.
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);

		// 데이터 넣기.
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);

		// unbind.
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		return VBO;
	}
	
	public static int createVAO(float[] vertices, float[] colors) {
		// VAO 만들기
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		// 각각의 attrib index에 VBO 할당하기.
		storeDataInAttributeList(0, 3, vertices);
		storeDataInAttributeList(1, 3, colors);
		
		// unbind.
		GL30.glBindVertexArray(0);
		return vaoID;
	}
	
	private static int storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		// vbo 만든다.
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		// 데이타 넣는다.
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		
		// 해당 index 활성화 시켜주고 VBO 가리키게 한다.
		GL20.glEnableVertexAttribArray(attributeNumber);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		
		// unbind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}
	
}
