package triangleRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import shader.Shader;
import util.Loader;

/* renderer # 4 */

// use shader to render triangles.

// OpenGL 2.0

public class ShaderRenderer implements TriangleRenderer{
	
	private final Shader shader;
	private final int vertexVBO, colorVBO;
	private final int n;
	
	public ShaderRenderer(float[] vertices, float[] colors){
		// create vbo
		vertexVBO = Loader.createVBO(vertices);
		colorVBO = Loader.createVBO(colors);
				
		this.shader = new Shader("src/shader/vertexShader.txt", "src/shader/fragmentShader.txt");
		shader.bindAttribute(0, "vertex");
		shader.bindAttribute(1, "color");
		
		this.n = vertices.length / 3;
		
	}
	
	@Override
	public void renderTriangle() {
		// TODO Auto-generated method stub
		// shader 프로그램 시작.
		shader.start();
			
		// vertex attrib 활성화.
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
			
		// vertex 정보 bind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexVBO);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			
		// color 정보 bind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVBO);
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
			
		// 그리기.
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, n);
			
		// unbind, disable
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
			
		// shader 프로그램 종료.
		shader.stop();
	}

}
