package triangleRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import shader.Shader;
import util.Loader;

/* renderer # 5 */

// A Vertex Array Object (VAO) is an OpenGL Object that stores all of the state needed to supply vertex data.

// OpenGL 3.0

public class VAORenderer implements TriangleRenderer{
	
	private final int vaoID;
	private final Shader shader;
	private final int n;
	
	public VAORenderer(float[] vertices, float[] colors){
		// enable vertex & color array
		this.shader = new Shader("res/shaders/triangleVertexShader.txt", "res/shaders/triangleFragmentShader.txt");
		shader.bindAttribute(0, "vertex");
		shader.bindAttribute(1, "color");
		
		//create vao
		vaoID = Loader.createVAO(vertices, colors);
		
		this.n = vertices.length / 3;
		
	}
	
	@Override
	public void renderTriangle() {
		shader.start();
		GL30.glBindVertexArray(vaoID);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, n);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

}
