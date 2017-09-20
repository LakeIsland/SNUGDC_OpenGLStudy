package engine;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

// this class handles GLFW related things.
// window, mouse, keybord input.. etc

public class Window {

	private final String title;

	private int width;

	private int height;

	private long windowHandle;

	private boolean vSync;
	private boolean isWindowed = true;

	public Window(String title, int width, int height, boolean vSync) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.vSync = vSync;
	}

	public void init() throws Exception {
		// Set error Callback.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Configure GLFW
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_COMPAT_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

		// Create the window
		windowHandle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if (windowHandle == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Setup a key callback. It will be called every time a key is pressed,
		// repeated or released.
		GLFW.glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
				GLFW.glfwSetWindowShouldClose(window, true);
			} else if (key == GLFW.GLFW_KEY_9 && action == GLFW.GLFW_RELEASE) {
				vSync = !vSync;
				updatevSync();
			} else if (key == GLFW.GLFW_KEY_F10 && action == GLFW.GLFW_RELEASE) {
				isWindowed = !isWindowed;
				updateScreenWindowFull();
				
			}
		});

		// Make the OpenGL context current
		GLFW.glfwMakeContextCurrent(windowHandle);

		// Center our window
		updateScreenWindowFull();

		// init update vsync
		updatevSync();

		// Make the window visible
		GLFW.glfwShowWindow(windowHandle);

		GL.createCapabilities();
		GL11.glViewport(0, 0, width, height);

		// Set the clear color
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public void terminate() {
		Callbacks.glfwFreeCallbacks(windowHandle);
		GLFW.glfwDestroyWindow(windowHandle);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

	public void setClearColor(float r, float g, float b, float alpha) {
		GL11.glClearColor(r, g, b, alpha);
	}

	public boolean windowShouldClose() {
		return GLFW.glfwWindowShouldClose(windowHandle);
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void updatevSync() {
		// Enable v-sync
		// v-sync란?
		// 모니터 대부분은 double buffer를 사용.
		// v-sync가 안되어 있으면 프레임 생성이 지나치게 빨라져서
		// 한 화면에 여러 화면이 그려질 수 있음.(tearing)
		// 프레임 생성과 출력 속도를 맞춤.
		// 프레임 1개를 생성할때 마다 바꿔주도록 설정.(2이상은 일반적으로 안쓰임)
		if (this.vSync) {
			GLFW.glfwSwapInterval(1);
		} else
			GLFW.glfwSwapInterval(0);
	}

	public void update() {
		GLFW.glfwSwapBuffers(windowHandle);
		GLFW.glfwPollEvents();
	}

	public void updateScreenWindowFull() {
		if (isWindowed)
			makeWindowedScreen();
		else
			makeFullScreen();
		
		updatevSync();
	}

	private void makeFullScreen() {
		GLFW.glfwSetWindowMonitor(windowHandle, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, GLFW.GLFW_DONT_CARE);
	}

	private void makeWindowedScreen() {
		GLFW.glfwSetWindowMonitor(windowHandle, MemoryUtil.NULL, 0, 0, width, height, GLFW.GLFW_DONT_CARE);
		makeWindowCentered();
	}

	private void makeWindowCentered() {
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(windowHandle, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
	}
}