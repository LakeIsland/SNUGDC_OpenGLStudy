package skyRenderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import input.KeyboardInput;
import models.RawModel;
import shader.Shader;
import texture.CubeTexture;
import util.Loader;
import util.Maths;

public class SkyRenderer {
	
	private Shader shader;
	private CubeTexture dayTexture;
	private CubeTexture nightTexture;
	
	private float time = 0;
	
	private static final float SIZE = 1000f;
	private static final float[] VERTICES = {        
		    -SIZE,  SIZE, -SIZE,
		    -SIZE, -SIZE, -SIZE,
		    SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		    -SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE
		};
	private RawModel cube;
	
	private static String[] DAY_TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"};
	private static String[] NIGHT_TEXTURE_FILES  = {"nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront"};
	
	public SkyRenderer(){
		cube = Loader.loadRawModelWithPos(VERTICES, 3);
		
		this.dayTexture = new CubeTexture(DAY_TEXTURE_FILES);
		this.nightTexture = new CubeTexture(NIGHT_TEXTURE_FILES);
		
		this.shader = new Shader("res/shaders/sky/skyboxVertexShader.txt", "res/shaders/sky/skyboxFragmentShader.txt");
		shader.bindAttribute(0, "position");

		shader.start();
		shader.setInteger("cubeMap", 0);
		shader.setInteger("cubeMap2", 1);
		shader.stop();
	}
	
	public void render(Matrix4f projectionMatrix, Camera camera){
		shader.start();
		
		shader.setMatrix4f("projectionMatrix", projectionMatrix);
		loadViewMatrix(camera);
		
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		bindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	private static final float ROTATE_SPEED = 1f;
	private float rotation = 0;

	private void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.getViewMatrix(camera);
		viewMatrix.m30(0);
		viewMatrix.m31(0); 
		viewMatrix.m32(0);
		viewMatrix.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0));
		shader.setMatrix4f("viewMatrix", viewMatrix);
	}
	
	public void update(float dt){
		this.time += dt * 100;
		this.time += 24000;
		this.time %= 24000;
		rotation += ROTATE_SPEED * dt;
	}
	
	public void processInput(float dt){
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_P)){
			this.update(dt * 10);
		}
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_O)){
			this.update(-dt * 10);
		}
	}
	
	private void bindTextures(){
		CubeTexture texture1;
		CubeTexture texture2;
		float blendFactor;		
		if(time >= 0 && time < 5000){
			texture1 = nightTexture;
			texture2 = nightTexture;
			blendFactor = (time - 0)/(5000 - 0);
		}else if(time >= 5000 && time < 8000){
			texture1 = nightTexture;
			texture2 = dayTexture;
			blendFactor = (time - 5000)/(8000 - 5000);
		}else if(time >= 8000 && time < 21000){
			texture1 = dayTexture;
			texture2 = dayTexture;
			blendFactor = (time - 8000)/(21000 - 8000);
		}else{
			texture1 = dayTexture;
			texture2 = nightTexture;
			blendFactor = (time - 21000)/(24000 - 21000);
		}

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		texture1.bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		texture2.bind();
		
		shader.setFloat("blendFactor", blendFactor);
	}
}
