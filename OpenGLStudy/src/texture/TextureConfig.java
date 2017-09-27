package texture;

import org.lwjgl.opengl.GL11;


public class TextureConfig {
	
	// repeat, clamp, clamp to edge
	private int wrapS;
	private int wrapT;
	private int minFilter;
	private int magFilter;
	private boolean useMipMap;
	
	public TextureConfig(){
	}

	public void setWrapS(int wrapS) {
		this.wrapS = wrapS;
	}

	public void setWrapT(int wrapT) {
		this.wrapT = wrapT;
	}

	public void setUseMipMap(boolean useMipMap) {
		this.useMipMap = useMipMap;
	}

	public void setMinFilter(int minFilter) {
		this.minFilter = minFilter;
	}

	public void setMagFilter(int magFilter) {
		this.magFilter = magFilter;
	}
	
	public int getWrapS() {
		return wrapS;
	}

	public int getWrapT() {
		return wrapT;
	}

	public boolean isUseMipMap() {
		return useMipMap;
	}

	public int getMinFilter() {
		return minFilter;
	}

	public int getMagFilter() {
		return magFilter;
	}
	
	private static TextureConfig normalConfig;
	
	public static TextureConfig getNormalTextureConfig(){
		if(normalConfig == null){
			normalConfig = new TextureConfig();
			normalConfig.setWrapS(GL11.GL_REPEAT);
			normalConfig.setWrapT(GL11.GL_REPEAT);
			
			normalConfig.setUseMipMap(true);
			normalConfig.setMinFilter(GL11.GL_LINEAR_MIPMAP_LINEAR);
			normalConfig.setMagFilter(GL11.GL_LINEAR);
			
			//normalConfig.setMinFilter(GL11.GL_LINEAR);
			//normalConfig.setMaxFilter(GL11.GL_LINEAR);
		}
		
		return normalConfig;
	}
}
