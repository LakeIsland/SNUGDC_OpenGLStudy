package terrain;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MapConstants {
	public static int MAP_BLOCK_SIZE = 64;
	public static int MAX_LOD_LEVEL = 6;
	
	public static float MAP_SCALE = 1f;
	public static float HEIGHT_SCALE = 1f;
	
	public static String RES_FOLDER = "res/config/islandConfig/";
	public static String MAP_TILE_FOLDER = "res/texture/map/tiles/";
	
	public static String MAP_HEIGHT_FILE;

	public static String MAP_TEXTURE_FILE;
	public static String MAP_NORMALMAP;
	
	public static String MAP_BLENDMAP;
	public static String MAP_BACKGROUND_TEXTURE;
	public static String MAP_RED_TEXTURE;
	public static String MAP_GREEN_TEXTURE;
	public static String MAP_BLUE_TEXTURE;
	
	public static String MAP_BACKGROUND_NORMAL_TEXTURE;
	public static String MAP_RED_NORMAL_TEXTURE;
	public static String MAP_GREEN_NORMAL_TEXTURE;
	public static String MAP_BLUE_NORMAL_TEXTURE;
	
	public static int MAP_SIZE;
	
	public static float MAP_MIN_HEIGHT;
	public static float MAP_MAX_HEIGHT;
	public static float MAP_HEIGHT_RANGE;
	
	
	private static int BYTE_NUMBER = 16;
	public static float MAP_INCREMENT_PER_BIT;
	
	public static int MAP_BLOCK_NUMBER;
	
	private static String mapFolder;
	
	public static int dataType = 2; // 0: png 1: raw;
	
	public static String MAP_NAME;
	public static boolean HAS_BLEND_MAP;
	
	public static void initFromXML(String mapName){
		mapFolder = RES_FOLDER + mapName + "/";
		String mapInitInfo = mapFolder + "config.xml";
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(mapInitInfo));
			
			Element mapEle = (Element)doc.getElementsByTagName("map").item(0);
			
			MAP_SIZE = Integer.parseInt(getString(mapEle, "size"));
			MAP_NAME = getString(mapEle, "name");
			HAS_BLEND_MAP = Boolean.parseBoolean(getString(mapEle,"hasBlendMap"));
			
			MAP_BLOCK_NUMBER = MAP_SIZE / MAP_BLOCK_SIZE;
			
			MAP_MIN_HEIGHT = Float.parseFloat(getString(mapEle, "minHeight"));
			MAP_MAX_HEIGHT = Float.parseFloat(getString(mapEle, "maxHeight"));
			MAP_HEIGHT_RANGE = MAP_MAX_HEIGHT - MAP_MIN_HEIGHT;
			MAP_INCREMENT_PER_BIT = MAP_HEIGHT_RANGE / (1 << BYTE_NUMBER);
			
			if(HAS_BLEND_MAP){
				try{
					Element diffuseEle = (Element)mapEle.getElementsByTagName("diffuse").item(0);
					MAP_BACKGROUND_TEXTURE = MAP_TILE_FOLDER + getString(diffuseEle, "background");
					MAP_RED_TEXTURE = MAP_TILE_FOLDER + getString(diffuseEle, "red");
					MAP_GREEN_TEXTURE = MAP_TILE_FOLDER + getString(diffuseEle, "green");
					MAP_BLUE_TEXTURE = MAP_TILE_FOLDER + getString(diffuseEle, "blue");
					
				} catch(Exception e){
					e.printStackTrace();
				} try{
					Element normalEle = (Element)mapEle.getElementsByTagName("normal").item(0);
					if(normalEle != null){
						MAP_BACKGROUND_NORMAL_TEXTURE = MAP_TILE_FOLDER + (getString(normalEle, "background"));
						MAP_RED_NORMAL_TEXTURE = MAP_TILE_FOLDER + (getString(normalEle, "red"));
						MAP_GREEN_NORMAL_TEXTURE = MAP_TILE_FOLDER + (getString(normalEle, "green"));
						MAP_BLUE_NORMAL_TEXTURE = MAP_TILE_FOLDER + (getString(normalEle, "blue"));
					}
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
				MAP_BLENDMAP = mapFolder + "blendMap.png";
			}
			
			MAP_NORMALMAP = mapFolder + "normalMap.png";
			MAP_HEIGHT_FILE = mapFolder + (getString(mapEle, "heightMap"));
			MAP_TEXTURE_FILE = mapFolder + (getString(mapEle, "textureMap"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String getString(Element ele, String name){
		return ele.getElementsByTagName(name).item(0).getTextContent();
	}
}
