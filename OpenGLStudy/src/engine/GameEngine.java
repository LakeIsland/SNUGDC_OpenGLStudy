package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import game.Game;

public class GameEngine {
	private final Window window;
	private final FPSCounter fpsCounter;
	private final IGameLogic gameLogic;

	private GameEngine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic) {
		this.window = new Window(windowTitle, width, height, vSync);
		this.gameLogic = gameLogic;
		this.fpsCounter = new FPSCounter();
	}

	private void init() throws Exception{
		// window should be initialized first 
		// -> GLFW, GL init should be preceded to loading image, mesh file etc...
		window.init();
		
		// then init game. 
		gameLogic.init();
	}

	private void loop() {
		float lastFrameTime = (float) GLFW.glfwGetTime();
		float dt = 0;
		
		while (!window.windowShouldClose()) {

			// get delta time
			float currentFrameTime = (float) GLFW.glfwGetTime();
			dt = currentFrameTime - lastFrameTime;
			lastFrameTime = currentFrameTime;
			
			// update that requires dt.
			fpsCounter.update(dt);
			gameLogic.update(dt);
			
			// process input
			gameLogic.processInput(window);
			
			// render
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			gameLogic.render(window);
			
			// update GLFW related things
			window.update();
		}
	}

	private void terminate() {
		window.terminate();
	}

	public static void main(String args[]) {
		
		IGameLogic gameLogic = new Game();
		GameEngine gameEngine = new GameEngine("My first 3D Game", 1280, 720, true, gameLogic);
		
		try{
			gameEngine.init();
			gameEngine.loop();
		}catch(Exception e){
			e.printStackTrace();
		}
		gameEngine.terminate();
	}
}
