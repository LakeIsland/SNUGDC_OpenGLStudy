package util;

import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Window;

public class Maths {
	
	public static Vector3f X_AXIS = new Vector3f(1,0,0);
	public static Vector3f Y_AXIS = new Vector3f(0,1,0);
	public static Vector3f Z_AXIS = new Vector3f(0,0,1);
	
	
	public static Matrix4f getTransformationMatrix(Vector3f position, float rx, float ry, float rz, float scale){
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(position);
		matrix.rotate((float)Math.toRadians(rx), X_AXIS);
		matrix.rotate((float)Math.toRadians(ry), Y_AXIS);
		matrix.rotate((float)Math.toRadians(rz), Z_AXIS);
		matrix.scale(scale);
		return matrix;
	}
	
	
	public static Matrix4f getProjectionMatrix(Window window){
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.perspective((float)Math.toRadians(45), window.getWidth() / (float)window.getHeight(), 1, 1000);
		return matrix;
	}
	
	private static Random random = new Random();
	public static float getRandomFloat(){
		return random.nextFloat();
	}
	
	public static float getRandomFloatBetweenMinusOneAndOne(){
		return 2 * random.nextFloat() - 1;
	}
}
