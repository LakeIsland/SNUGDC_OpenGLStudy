package triangleRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import util.Loader;

/* renderer # 3 */

// Vertex buffer object (VBO) allows vertex array data to be 
// stored in high-performance graphics memory on the server(GPU) side and promotes efficient data transfer.
// reference
// http://www.songho.ca/opengl/gl_vbo.html

// OpenGL version 1.5

public class VBORenderer implements TriangleRenderer {
	private final int vertexVBO, colorVBO;

	private final int n;
	
	public VBORenderer(float[] vertices, float[] colors){
		// enable vertex & color array
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

		// create vbo
		vertexVBO = Loader.createVBO(vertices);
		colorVBO = Loader.createVBO(colors);
		n = vertices.length / 3;
	}

	

	@Override
	public void renderTriangle() {
		// TODO Auto-generated method stub

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexVBO);
		// 맨 마지막 pointer가 0이면 현재 array buffer에 bind된 값을 기준으로 생각함.
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVBO);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, n);
		// unbind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

}
