package input;

public class KeyboardInput {
	public static final int KEY_NUMBER = 1024;
	
	public static boolean[] keys = new boolean[KEY_NUMBER];
	public static boolean[] pushProcessed = new boolean[KEY_NUMBER];
	public static boolean[] releaseProcessed = new boolean[KEY_NUMBER];
	
	public static boolean posEdge(int key){
		if(keys[key] && !pushProcessed[key]){
			pushProcessed[key] = true;
			return true;
		}
		return false;
	}
	
	public static boolean negEdge(int key){
		if(!keys[key] && !releaseProcessed[key]){
			releaseProcessed[key] = true;
			return true;
		}
		return false;
	}
	
	public static boolean isPressed(int key){
		return keys[key];
	}
	
	public static boolean isReleased(int key){
		return !keys[key];
	}
}
