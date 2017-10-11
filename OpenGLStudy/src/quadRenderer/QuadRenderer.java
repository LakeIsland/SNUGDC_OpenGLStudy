package quadRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import shader.Shader;
import texture.Texture;
import texture.TextureLoader;
import util.Loader;

public class QuadRenderer {
	private int quad;
	private Shader shader;
	private Texture quadTexture;

	public QuadRenderer() {
		this.shader = new Shader("res/shaders/quadVertexShader.txt", "res/shaders/quadFragmentShader.txt");
		
		shader.bindAttribute(0, "position");
		shader.bindAttribute(1, "color");
		shader.bindAttribute(2, "texCoord");
		
		shader.setInteger("image", 0);
		
		
		float[] positions = { 0.5f, 0.5f, 0.0f, 0.5f, -0.5f, 0.0f, -0.5f, -0.5f, 0.0f, -0.5f, 0.5f, 0.0f };

		float[] colors = { 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 1f, 1f, 0f };

		float[] texCoords = { 1, 1, 1, 0, 0, 0, 0, 1 };

		int[] indices = { 0, 1, 3, 1, 2, 3 };

		//quad = Loader.loadPosColor(positions, colors, indices);
		quad = Loader.loadPosColorTex(positions, colors, texCoords, indices);
		quadTexture = TextureLoader.getNormalRGBTexture("res/texture/image.png");
	}

	public void render() {
		shader.start();
		GL30.glBindVertexArray(quad);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		quadTexture.bind();
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
}
