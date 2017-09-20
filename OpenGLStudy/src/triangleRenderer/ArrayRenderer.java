package triangleRenderer;

import org.lwjgl.opengl.GL11;

/* renderer # 2 */

// very primitive & old way to draw a triangle. better than begin end way 
// but still have to transfer data from CPU to GPU every frame.

// OpenGL version 1.1

public class ArrayRenderer implements TriangleRenderer{
	
	private final int n;
	
	public ArrayRenderer(float[] vertices, float[] colors){
		// enable vertex & color array
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

		// set pointer to data.
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertices);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, colors);
		
		this.n = vertices.length / 3;
	}
	
	public void renderTriangle() {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, n);	
	}
}
