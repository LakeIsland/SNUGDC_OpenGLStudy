package terrain;

import camera.Camera;

public class GeoMipMapPatch {
	private static int MAP_BLOCK_SIZE = MapConstants.MAP_BLOCK_SIZE;
	private static int MAX_LOD_LEVEL = MapConstants.MAX_LOD_LEVEL;
	
	private int gridX, gridY;

	public GeoMipMapPatch(int gridX, int gridY) {
		super();
		this.gridX = gridX;
		this.gridY = gridY;
	}
	
	public int calculateLodLevel(Camera camera) {
		int gridCenterX = gridX * MAP_BLOCK_SIZE + MAP_BLOCK_SIZE / 2;
		int gridCenterY = gridY * MAP_BLOCK_SIZE + MAP_BLOCK_SIZE / 2;
		
		int dx = Math.abs((int)(camera.getPosition().x / MapConstants.MAP_SCALE - gridCenterX)/ MAP_BLOCK_SIZE);
		int dy = Math.abs((int)(camera.getPosition().z / MapConstants.MAP_SCALE - gridCenterY) / MAP_BLOCK_SIZE);
		
		int LOD = Math.min(MAX_LOD_LEVEL, Math.max(dx, dy));
		
		return LOD;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}

}
