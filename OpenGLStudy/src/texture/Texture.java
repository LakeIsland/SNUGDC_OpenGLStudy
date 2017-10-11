package texture;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

public class Texture {
	private int width;
	private int height;
	private int id;
	
	public Texture(String path, int format, TextureConfig config){
		loadTextureFromPath(path, format);
		createMipMap(config.isUseMipMap());
		setParameter(config);
		unbind();
	}
	
	private void createMipMap(boolean useMipMap){
		if(useMipMap)
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
	}
	
	private void loadTextureFromPath(String path, int format){
		TextureData td = TextureData.loadImageData(path, format);
		
		Buffer image = td.getImage();
		
		// failed to create image -> error
		if(image == null){
			System.out.println(path);
			throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + STBImage.stbi_failure_reason());
		}
		
		width = td.getWidth();
		height = td.getHeight();
		
		id = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		image.flip();
		
		// send image data to GPU.
		if(format == TextureFormat.RGBA_8){
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width,
					height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) image);
		} else if (format == TextureFormat.GRAY_16){
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_R16, width,
					height, 0, GL11.GL_RED, GL11.GL_UNSIGNED_SHORT, (ShortBuffer)image);
		} else if (format == TextureFormat.GRAY_8){
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_R8, width,
					height, 0, GL11.GL_RED, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)image);
		}
		
		
	}
	
	private void setParameter(TextureConfig config){
		setParameter(GL11.GL_TEXTURE_WRAP_S, config.getWrapS());
		setParameter(GL11.GL_TEXTURE_WRAP_T, config.getWrapT());
		setParameter(GL11.GL_TEXTURE_MIN_FILTER, config.getMinFilter());
		setParameter(GL11.GL_TEXTURE_MAG_FILTER, config.getMagFilter());
	}
	
	private void setParameter(int name, int value) {
		if(value != 0)
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, name, value);
    }
	
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public static void unbind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
}
