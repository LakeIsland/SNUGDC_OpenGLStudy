package game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import camera.Camera;
import engine.IGameLogic;
import engine.Window;
import input.KeyboardInput;
import models.Entity;
import models.RawModel;
import models.TexturedModel;
import standardRenderer.StandardRenderer;
import terrain.Terrain;
import terrain.TerrainRenderer;
import texture.Texture;
import util.Maths;

public class Game implements IGameLogic {
	
	private Terrain terrain;
	private TerrainRenderer terrainTerrainRenderer;
	
	private Camera camera;
	
	private StandardRenderer standardRenderer;
	
	private List<Entity> normaltrees;
	private TexturedModel normalTreeModel;
	
	public Game() {}
	
	@Override
	public void init() {
		
		camera = new Camera();
		standardRenderer = new StandardRenderer();
		
		//RawModel testMesh = OBJLoader.loadOBJModel("res/mesh/barrel.obj");
		//Texture testTexture = TextureLoader.getNormalRGBTexture("res/texture/barrel.png");
		//TexturedModel testModel = new TexturedModel(testMesh, testTexture);
		
		//testEntity = new Entity(testModel, new Vector3f(0,0,100), 0, 0, 0, 2);
		
		terrain = new Terrain("MyIsland1");
		terrainTerrainRenderer = new TerrainRenderer();
		
		
		// ------------------------ ASSIGNMENT  -------------------------------// 
		
		
		// TODO : create trees on the map;
		// res/mesh/tree, res/texture/tree -> 5 types of tree
		// tree01 -> divided into two part; trunk, leaf
		
		RawModel normalTreeMesh = null;		// change null to other value.
		Texture normalTreeTexture = null;	// change null to other value.
		normalTreeModel = new TexturedModel(normalTreeMesh, normalTreeTexture);
		normaltrees = new ArrayList<Entity>();
		
		for(int i=0; i<100; i++){
			// TODO : add normal tree entity to the list. 
			// ex) normaltrees.add(new Entity(model, position, rotX, rotY, rotZ, scale));
			// IMPORTANT : set model y value from terrain height. -> use terrain.getExactHeight;
			
		}
		
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
		
		// if you finish implementing normaltreelist then activate following comment.
		//standardRenderer.render(normalTreeModel, normaltrees, Maths.getProjectionMatrix(window), Maths.getViewMatrix(camera));
		terrainTerrainRenderer.render(terrain, camera, Maths.getViewMatrix(camera), Maths.getProjectionMatrix(window));
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

}
