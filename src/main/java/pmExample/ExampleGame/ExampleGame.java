package pmExample.ExampleGame;

import static org.lwjgl.glfw.GLFW.*;

public class ExampleGame {
	
	public static void main(String[] args) throws InterruptedException {
		try {
			glfwInit();
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
			glfwWindowHint(GLFW_VERSION_MAJOR, 2);
			glfwWindowHint(GLFW_VERSION_MINOR, 1);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_FALSE);
			long windowPointer = glfwCreateWindow(800, 600, "Hi Window!", 0, 0);
			
			while (!glfwWindowShouldClose(windowPointer)) {
				glfwSwapBuffers(windowPointer);
				glfwPollEvents();
				
				Thread.sleep(1000 / 60);
			}
			
			glfwDestroyWindow(windowPointer);
			
		} finally {
			glfwTerminate();
		}
	}
}
