package terrain;

import texture.Texture;

public class TerrainTexturePackage {
	private Texture redChannelTexture;
	private Texture greenChannelTexture;
	private Texture blueChannelTexture;
	private Texture backgroundTexture;
	
	public TerrainTexturePackage(Texture redChannelTexture, Texture greenChannelTexture, Texture blueChannelTexture,
			Texture backgroundTexture) {
		super();
		this.redChannelTexture = redChannelTexture;
		this.greenChannelTexture = greenChannelTexture;
		this.blueChannelTexture = blueChannelTexture;
		this.backgroundTexture = backgroundTexture;
	}
	
	public Texture getRedChannelTexture() {
		return redChannelTexture;
	}
	public Texture getGreenChannelTexture() {
		return greenChannelTexture;
	}
	public Texture getBlueChannelTexture() {
		return blueChannelTexture;
	}
	public Texture getBackgroundTexture() {
		return backgroundTexture;
	}
	
	
	
}
