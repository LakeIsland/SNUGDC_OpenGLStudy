package triangleRenderer;

import java.util.Random;

public class TriangleData {
	
	private final float[] vertices;
	private final float[] colors;
	
	public TriangleData(){
		vertices = new float[]{ 0, 0.5f, 0, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0 };
		colors = new float[]{ 1, 0, 0, 0, 1, 0, 0, 0, 1 };
	}
	
	public TriangleData(int n){
		Random random = new Random();
		
		vertices = new float[9 * n];
		colors = new float[9 * n];
		
		int pointer = 0;
		for(int i=0; i<3 * n; i++){
			vertices[pointer++] = 2 * random.nextFloat() - 1;
			vertices[pointer++] = 2 * random.nextFloat() - 1;
			vertices[pointer++] = 0;
		}
		
		float[] rgb_colors = { 1, 0, 0, 0, 1, 0, 0, 0, 1 };

		for(int i=0; i<9 * n; i++){
			colors[i] = rgb_colors[i % 9];
		}	
	}

	public float[] getVerticeArray() {
		return vertices;
	}

	public float[] getColorArray() {
		return colors;
	}
	
	

}
