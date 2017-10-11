package game;

import org.joml.Vector3f;

import camera.Camera;
import cubeRenderer.CubeData;
import cubeRenderer.CubeRenderer;
import engine.IGameLogic;
import engine.Window;
import models.Entity;
import models.RawModel;
import models.TexturedModel;
import quadRenderer.QuadRenderer;
import resourceLoader.OBJLoader;
import standardRenderer.StandardRenderer;
import texture.Texture;
import texture.TextureLoader;
import triangleRenderer.TriangleRenderer;
import util.Maths;

public class Game implements IGameLogic {

	private TriangleRenderer triangleRenderer;
	private QuadRenderer quadRenderer;
	private CubeRenderer cubeRenderer;
	private CubeData cubeData;
	private Camera camera;
	
	private StandardRenderer standardRenderer;
	private Entity testEntity;
	
	public Game() {}
	
	@Override
	public void init() {
		
		// TriangleData data = new TriangleData(1000);	// 1000 random triangles
		// TriangleData data = new TriangleData();			// single triangle
		
		// try different renderers
																									// index - version
		//triangleRenderer = new BeginEndRenderer(data.getVerticeArray(), data.getColorArray());	// 1 - 1.1
		//triangleRenderer = new ArrayRenderer(data.getVerticeArray(), data.getColorArray());		// 2 - 1.1
		//triangleRenderer = new VBORenderer(data.getVerticeArray(), data.getColorArray());			// 3 - 1.5
		//triangleRenderer = new ShaderRenderer(data.getVerticeArray(), data.getColorArray());		// 4 - 2.0
		//triangleRenderer = new VAORenderer(data.getVerticeArray(), data.getColorArray());			// 5 - 3.0
		
		
		//quadRenderer = new QuadRenderer();
		
		cubeData = new CubeData(100);
		cubeRenderer = new CubeRenderer();
		
		camera = new Camera();
		
		standardRenderer = new StandardRenderer();
		
		RawModel testMesh = OBJLoader.loadOBJModel("res/mesh/barrel.obj");
		Texture testTexture = TextureLoader.getNormalRGBTexture("res/texture/barrel.png");
		TexturedModel testModel = new TexturedModel(testMesh, testTexture);
		
		testEntity = new Entity(testModel, new Vector3f(0,0,100), 0, 0, 0, 2);
	}

	@Override
	public void processInput(Window window, float interval) {
		camera.processInput(interval);
		camera.mouseInput(window, interval);
	}

	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub
		cubeData.update(interval);
	}

	@Override
	public void render(Window window) {
		//quadRenderer.render();
		cubeRenderer.render(cubeData, Maths.getProjectionMatrix(window), Maths.getViewMatrix(camera));
		standardRenderer.render(testEntity, Maths.getProjectionMatrix(window), Maths.getViewMatrix(camera));
	}

}
