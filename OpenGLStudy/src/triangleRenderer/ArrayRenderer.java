package triangleRenderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
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
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
		buffer.put(vertices);
		buffer.flip();
		
		
		// set pointer to data.
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, buffer);
		
		FloatBuffer buffer2 = BufferUtils.createFloatBuffer(colors.length);
		buffer2.put(colors);
		buffer2.flip();
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, buffer2);
		
		this.n = vertices.length / 3;
	}
	
	public void renderTriangle() {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, n);	
	}
}
