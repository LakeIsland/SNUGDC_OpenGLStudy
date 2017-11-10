package game;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import camera.Camera;
import cubeRenderer.CubeData;
import cubeRenderer.CubeRenderer;
import engine.IGameLogic;
import engine.Window;
import input.KeyboardInput;
import models.Entity;
import models.RawModel;
import models.TexturedModel;
import quadRenderer.QuadRenderer;
import resourceLoader.OBJLoader;
import standardRenderer.StandardRenderer;
import terrain.MapConstants;
import terrain.Terrain;
import terrain.TerrainRenderer;
import texture.Texture;
import texture.TextureLoader;
import triangleRenderer.TriangleRenderer;
import util.Maths;

public class Game implements IGameLogic {

	private TriangleRenderer triangleRenderer;
	private QuadRenderer quadRenderer;
	private CubeRenderer cubeRenderer;
	private CubeData cubeData;
	
	private Terrain terrain;
	private TerrainRenderer terrainTerrainRenderer;
	
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
		
		//cubeData = new CubeData(100);
		//cubeRenderer = new CubeRenderer();
		
		camera = new Camera();
		
		//standardRenderer = new StandardRenderer();
		
		//RawModel testMesh = OBJLoader.loadOBJModel("res/mesh/barrel.obj");
		//Texture testTexture = TextureLoader.getNormalRGBTexture("res/texture/barrel.png");
		//TexturedModel testModel = new TexturedModel(testMesh, testTexture);
		
		//testEntity = new Entity(testModel, new Vector3f(0,0,100), 0, 0, 0, 2);
		
		terrain = new Terrain("Fjord");
		terrainTerrainRenderer = new TerrainRenderer();
	}
	
	private boolean isLine = false;
	
	@Override
	public void processInput(Window window, float interval) {
		camera.processInput(interval);
		camera.mouseInput(window, interval);
		if(KeyboardInput.posEdge(GLFW.GLFW_KEY_6)){
			isLine = !isLine;
		}
	}

	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub
		//cubeData.update(interval);
		terrain.updateLODLevel(camera);
		camera.attachTerrain(terrain);
		
	}

	@Override
	public void render(Window window) {
		if(!isLine){
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}else{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		//quadRenderer.render();
		//cubeRenderer.render(cubeData, Maths.getProjectionMatrix(window), Maths.getViewMatrix(camera));
		//standardRenderer.render(testEntity, Maths.getProjectionMatrix(window), Maths.getViewMatrix(camera));
		terrainTerrainRenderer.render(terrain, camera, Maths.getViewMatrix(camera), Maths.getProjectionMatrix(window));
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

}
