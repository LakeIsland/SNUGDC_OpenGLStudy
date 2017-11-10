package camera;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import engine.Window;
import input.KeyboardInput;
import input.MouseInput;
import terrain.Terrain;

public class Camera {
	private Vector3f position;
	
	private float pitch;
	private float yaw;
	private float roll;
	
	private static final float SPEED = 10;
	private static final float ROT_SPEED = 10;
	private static final float MOUSE_SENSITIVITY = 0.2f;
	private static final float EYE_HEIGHT = 1.5f;
	
	private boolean isFlying = false;
	
	public Camera(){
		position = new Vector3f();
		pitch = 0;
		yaw = 0;
		roll = 0;
	}
	
	public void processInput(float dt){
		
		float moveAmount = SPEED * dt;
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT)){
			moveAmount  *= 2;
		}
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_W)){
			position.z += -1 * moveAmount * Math.cos(Math.toRadians(yaw));
			position.x += -1 * moveAmount * Math.sin(Math.toRadians(yaw));
		}
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_S)){
			position.z += -1 * moveAmount * Math.cos(Math.toRadians(yaw + 180));
			position.x += -1 * moveAmount * Math.sin(Math.toRadians(yaw + 180));
		}
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_D)){
			position.z += -1 * moveAmount * Math.cos(Math.toRadians(yaw + 270));
			position.x += -1 * moveAmount * Math.sin(Math.toRadians(yaw + 270));
		}
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_A)){
			position.z += -1 * moveAmount * Math.cos(Math.toRadians(yaw + 90));
			position.x += -1 * moveAmount * Math.sin(Math.toRadians(yaw + 90));
		}
		
		if (KeyboardInput.isPressed(GLFW.GLFW_KEY_T)) {
			position.y += moveAmount;
		}
		if (KeyboardInput.isPressed(GLFW.GLFW_KEY_G)) {
			position.y -= moveAmount;
		}
		
		float rotAmount = ROT_SPEED * dt;
		
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_LEFT)){
			yaw += rotAmount;
		}if(KeyboardInput.isPressed(GLFW.GLFW_KEY_RIGHT)){
			yaw -= rotAmount;
		}
		
		if(KeyboardInput.isPressed(GLFW.GLFW_KEY_UP)){
			pitch += rotAmount;
		}if(KeyboardInput.isPressed(GLFW.GLFW_KEY_DOWN)){
			pitch -= rotAmount;
		}
		
		if(KeyboardInput.posEdge(GLFW.GLFW_KEY_5)){
			isFlying = !isFlying;
			System.out.println("CALLED");
		}
	}
	
	public void attachTerrain(Terrain terrain){
		if(!isFlying){
			float height = terrain.getExactHeight(position.x, position.z);
			position.y = height + EYE_HEIGHT;
		}
	}
	
	public void mouseInput(Window window, float delta){
		float dx = window.getWidth() * 0.5f - MouseInput.xPos;
		float dy = window.getHeight() * 0.5f - MouseInput.yPos;
		
		float rotAmount = MOUSE_SENSITIVITY * ROT_SPEED * delta;
		yaw += rotAmount * dx;
		pitch += rotAmount * dy;
		if(pitch < -90)
			pitch = -90;
		if(pitch > 90)
			pitch = 90;
		
		window.setCursorCenter();
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}
	
}
