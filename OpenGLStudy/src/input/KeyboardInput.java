package input;

public class KeyboardInput {
	public static final int KEY_NUMBER = 1024;
	
	public static boolean[] keys = new boolean[KEY_NUMBER];
	public static boolean[] beforeKeys = new boolean[KEY_NUMBER];
	
	public static boolean posEdge(int key){
		return (!beforeKeys[key]) && keys[key];
	}
	
	public static boolean negEdge(int key){
		return beforeKeys[key] && (!keys[key]);
	}
	
	public static boolean isPressed(int key){
		return keys[key];
	}
	
	public static boolean isReleased(int key){
		return !keys[key];
	}
}
