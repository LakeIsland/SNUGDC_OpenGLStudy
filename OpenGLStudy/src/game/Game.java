package game;

import engine.IGameLogic;
import engine.Window;
import triangleRenderer.TriangleData;
import triangleRenderer.TriangleRenderer;
import triangleRenderer.VAORenderer;

public class Game implements IGameLogic {

	public Game() {}
	private TriangleRenderer triangleRenderer;
	
	@Override
	public void init() {
		
		// TriangleData data = new TriangleData(1000);	// 1000 random triangles
		TriangleData data = new TriangleData();			// single triangle
		
		// try different renderers
																									// index - version
		//triangleRenderer = new BeginEndRenderer(data.getVerticeArray(), data.getColorArray());	// 1 - 1.1
		//triangleRenderer = new ArrayRenderer(data.getVerticeArray(), data.getColorArray());		// 2 - 1.1
		//triangleRenderer = new VBORenderer(data.getVerticeArray(), data.getColorArray());			// 3 - 1.5
		//triangleRenderer = new ShaderRenderer(data.getVerticeArray(), data.getColorArray());		// 4 - 2.0
		triangleRenderer = new VAORenderer(data.getVerticeArray(), data.getColorArray());			// 5 - 3.0
	
	}

	@Override
	public void processInput(Window window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Window window) {
		triangleRenderer.renderTriangle();
	}

}
