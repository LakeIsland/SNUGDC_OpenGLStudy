package texture;

import java.nio.Buffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

public class TextureData{
	
	private Buffer image = null;
	private int width, height;
	
	private TextureData(Buffer image, int width, int height) {
		this.image = image;
		this.width = width;
		this.height = height;
	}
	
	public void clear(){
		if(image!=null)
			MemoryUtil.memFree(image);
	}

	public Buffer getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public static TextureData loadImageData(String path, int type){
		Buffer image = null;
		IntBuffer w = MemoryUtil.memAllocInt(1);
		IntBuffer h = MemoryUtil.memAllocInt(1);
		IntBuffer comp = MemoryUtil.memAllocInt(1);
		
		if(type == TextureFormat.RGBA_8){
			image = STBImage.stbi_load(path, w, h, comp, 4);
		} else if(type == TextureFormat.GRAY_16){
			image = STBImage.stbi_load_16(path, w, h, comp, 1);
		} else if(type == TextureFormat.GRAY_8){
			image = STBImage.stbi_load(path, w, h, comp, 1);
		}
		
		return new TextureData(image, w.get(0), h.get(0));
	}
	
	
}
