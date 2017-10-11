package cubeRenderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import engine.Window;
import models.Entity;
import models.RawModel;
import models.TexturedModel;
import shader.Shader;
import texture.Texture;
import texture.TextureLoader;
import util.Loader;
import util.Maths;

public class CubeRenderer {
	private Shader shader;
	
	public CubeRenderer() {
		this.shader = new Shader("res/shaders/cubeVertexShader.txt", "res/shaders/cubeFragmentShader.txt");

		shader.bindAttribute(0, "position");
		shader.bindAttribute(1, "texCoord");
		
		shader.start();
		shader.setInteger("image", 0);
		shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	
	public void render(CubeData cubeData, Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		
		List<Entity> cubeEntities = cubeData.getCubeEntities();
		TexturedModel cubeModel = cubeData.getCubeModel();
		
		shader.start();
		cubeData.getCubeModel().getRawModel().bind();

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		cubeData.getCubeModel().getTexture().bind();
		
		shader.setMatrix4f("viewMatrix", viewMatrix);
		shader.setMatrix4f("projectionMatrix", projectionMatrix);
		
		
		for(Entity cubeEntity : cubeEntities){
			shader.setMatrix4f("modelMatrix", Maths.getTransformationMatrix(cubeEntity.getPosition(), cubeEntity.getRotX(), cubeEntity.getRotY(), cubeEntity.getRotZ(), 1));
			GL11.glDrawElements(GL11.GL_TRIANGLES, cubeModel.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindVertexArray(0);
		shader.stop();
		
	}
}
