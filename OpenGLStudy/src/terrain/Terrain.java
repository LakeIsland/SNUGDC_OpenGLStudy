package terrain;

import camera.Camera;
import texture.Texture;
import texture.TextureLoader;

public class Terrain {
	private GeoMipMapPatch[][] mapBlocks;
	private int[][] lodTables;
	
	private final Texture terrainTexture;
	private final Texture terrainHeightMapTexture;
	
	
	private final boolean hasBlendMap;
	private Texture blendMap;
	private TerrainTexturePackage terrainTexturePackage;
	
	private final GeoMipMapPatchMesh geoMipMapPatchMesh;
	
	public Terrain(String terrainName){
		
		MapConstants.initFromXML(terrainName);
		
		hasBlendMap = MapConstants.HAS_BLEND_MAP;
		
		geoMipMapPatchMesh = new GeoMipMapPatchMesh(MapConstants.MAP_BLOCK_SIZE, MapConstants.MAX_LOD_LEVEL);
		terrainTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_TEXTURE_FILE);
		terrainHeightMapTexture = TextureLoader.getNormalGray16Texture(MapConstants.MAP_HEIGHT_FILE);
		
		if(hasBlendMap){
			blendMap = TextureLoader.getNormalRGBTexture(MapConstants.MAP_BLENDMAP);
			Texture rTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_RED_TEXTURE);
			Texture gTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_GREEN_TEXTURE);
			Texture bTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_BLUE_TEXTURE);
			Texture backgroundTexture = TextureLoader.getNormalRGBTexture(MapConstants.MAP_BACKGROUND_TEXTURE);
			terrainTexturePackage = new TerrainTexturePackage(rTexture, gTexture, bTexture, backgroundTexture);
		}
		
		mapBlocks = new GeoMipMapPatch[MapConstants.MAP_BLOCK_NUMBER][MapConstants.MAP_BLOCK_NUMBER];
		lodTables = new int[MapConstants.MAP_BLOCK_NUMBER][MapConstants.MAP_BLOCK_NUMBER];
		
		for (int i = 0; i < MapConstants.MAP_BLOCK_NUMBER; i++) {
			for (int j = 0; j < MapConstants.MAP_BLOCK_NUMBER; j++) {
				GeoMipMapPatch gmmp = new GeoMipMapPatch(i, j);
				mapBlocks[i][j] = gmmp;
			}
		}
	}
	
	public void updateLODLevel(Camera camera){
		for (int i = 0; i < MapConstants.MAP_BLOCK_NUMBER; i++) {
			for (int j = 0; j < MapConstants.MAP_BLOCK_NUMBER; j++) {
				lodTables[i][j] = mapBlocks[i][j].calculateLodLevel(camera);
			}
		}
	}

	public GeoMipMapPatch[][] getMapBlocks() {
		return mapBlocks;
	}

	public int[][] getLodTables() {
		return lodTables;
	}

	public TerrainTexturePackage getTerrainTexturePackage() {
		return terrainTexturePackage;
	}
	
	public Texture getBlendMap(){
		return blendMap;
	}
	
	public Texture getTerrainTexture(){
		return terrainTexture;
	}
	
	public Texture getTerrainHeightMapTexture(){
		return terrainHeightMapTexture;
	}

	public GeoMipMapPatchMesh getGeoMipMapPatchMesh() {
		return geoMipMapPatchMesh;
	}
	
	
}
