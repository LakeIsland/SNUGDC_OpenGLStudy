package texture;

import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

public class TerrainTexture extends Texture{
	public TerrainTexture(int width, int height, short[] data){
		super();
		GL11.glTexImage2D(GL31.GL_TEXTURE_RECTANGLE, 0, GL30.GL_R16, width,
				height, 0, GL11.GL_RED, GL11.GL_UNSIGNED_SHORT, data);
		setParameter(TextureConfig.getheightMapTextureConfig());
		unbind();
	}
}
