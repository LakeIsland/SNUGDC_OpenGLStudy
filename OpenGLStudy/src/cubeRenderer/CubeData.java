package cubeRenderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import engine.DtUpdatable;
import models.Entity;
import models.RawModel;
import models.TexturedModel;
import texture.Texture;
import texture.TextureLoader;
import util.Loader;
import util.Maths;

public class CubeData implements DtUpdatable{

	private TexturedModel cubeModel;
	private List<Entity> cubeEntities;
	
	public CubeData(int number){
		createCubeModel();
		createCubeEntities(number);
	}
	
	private void createCubeModel(){
		float[] vertices = { -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,

				-0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

				0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

				-0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,

				-0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,

				-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f

		};

		float[] textureCoords = {

				0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
				1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0

		};

		int[] indices = { 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17, 19,
				19, 17, 18, 20, 21, 23, 23, 21, 22
		};
		
		RawModel cubeMesh = Loader.loadToVao(vertices, textureCoords, indices);
		Texture cubeTexture = TextureLoader.getNormalRGBTexture("res/texture/image.png");
		
		cubeModel = new TexturedModel(cubeMesh, cubeTexture);
		
	}
	
	private void createCubeEntities(int cubeNumber){
		cubeEntities = new ArrayList<Entity>();
		for(int i=0; i<cubeNumber; i++){
			float x = 20 * Maths.getRandomFloatBetweenMinusOneAndOne();
			float y = 20 * Maths.getRandomFloatBetweenMinusOneAndOne();
			float z = 20 * Maths.getRandomFloatBetweenMinusOneAndOne() - 25;
			
			float rx = 180 * Maths.getRandomFloatBetweenMinusOneAndOne();
			float ry = 180 * Maths.getRandomFloatBetweenMinusOneAndOne();
			float rz = 180 * Maths.getRandomFloatBetweenMinusOneAndOne();
			
			
			float scale = 1 + 0.2f * Maths.getRandomFloatBetweenMinusOneAndOne();
			cubeEntities.add(new Entity(cubeModel, new Vector3f(x,y,z), rx, ry, rz, scale));
		}
	}
	
	@ Override
	public void update(float dt){
		for(Entity cubeEntity : cubeEntities){
			cubeEntity.setRotY(cubeEntity.getRotY() + 10 * dt);
		}
	}

	public TexturedModel getCubeModel() {
		return cubeModel;
	}

	public List<Entity> getCubeEntities() {
		return cubeEntities;
	}
	
	
}
