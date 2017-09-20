package triangleRenderer;

import org.lwjgl.opengl.GL11;

/* renderer # 1 */

// very primitive & old way to draw a triangle.

// OpenGL version 1.1

public class BeginEndRenderer implements TriangleRenderer{
	private final float[] vertices;
	private final float[] colors;
	
	public BeginEndRenderer(float[] vertices, float[] colors){
		this.vertices = vertices;
		this.colors = colors;
	}
	
	@Override
	public void renderTriangle() {
		/* Render triangle */
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		int n = vertices.length / 3;
		int verticePointer = 0;
		int colorPointer = 0;
		
		for(int i=0; i<n; i++){
			GL11.glColor3f(colors[colorPointer++], colors[colorPointer++], colors[colorPointer++]);
			GL11.glVertex3f(vertices[verticePointer++], vertices[verticePointer++], vertices[verticePointer++]);
		}

		GL11.glEnd();
	}

}
