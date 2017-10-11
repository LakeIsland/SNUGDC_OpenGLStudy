package standardRenderer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import models.Entity;
import shader.Shader;
import util.Maths;

public class StandardRenderer {

	private Shader shader;

	public StandardRenderer() {
		this.shader = new Shader("res/shaders/standardVertexShader.txt", "res/shaders/standardFragmentShader.txt");

		shader.bindAttribute(0, "position");
		shader.bindAttribute(1, "texCoord");
		shader.bindAttribute(2, "normal");

		shader.start();
		shader.setInteger("image", 0);
		shader.stop();
	}

	public void render(Entity entity, Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		shader.start();
		entity.getModel().getRawModel().bind();

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		entity.getModel().getTexture().bind();
		
		shader.setMatrix4f("viewMatrix", viewMatrix);
		shader.setMatrix4f("projectionMatrix", projectionMatrix);
		
		
		shader.setMatrix4f("modelMatrix", Maths.getTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()));
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
}
