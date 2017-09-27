package texture;

public class TextureLoader {
	
	public static Texture getNormalRGBTexture(String path){
		return new Texture(path, TextureFormat.RGBA_8, TextureConfig.getNormalTextureConfig());
	}
	
	public static Texture getNormalGray8Texture(String path){
		return new Texture(path, TextureFormat.GRAY_8, TextureConfig.getNormalTextureConfig());
	}
	
}
