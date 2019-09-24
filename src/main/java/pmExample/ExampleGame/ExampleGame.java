package pmExample.ExampleGame;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class ExampleGame {
	
	public static void main(String[] args) {
		new ExampleGame().run();
	}
	
	private long windowPointer;
	
	public void run() {
		try {
			glfwInit();
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
			glfwWindowHint(GLFW_VERSION_MAJOR, 2);
			glfwWindowHint(GLFW_VERSION_MINOR, 1);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_FALSE);
			windowPointer = glfwCreateWindow(800, 600, "Hi Window!", 0, 0);
			glfwMakeContextCurrent(windowPointer);
			GL.createCapabilities();
			setup();
			
			while (!glfwWindowShouldClose(windowPointer)) {
				update();
				
				render();
				glfwSwapBuffers(windowPointer);
				glfwPollEvents();
				
				try {
					Thread.sleep(1000 / 60);
				} catch (InterruptedException ignored) {
				
				}
			}
			
			release();
			glfwDestroyWindow(windowPointer);
			
		} finally {
			glfwTerminate();
		}
	}
	
	public void setup() {
	
	}
	
	public void release() {
	
	}
	
	public void update() {
	
	}
	
	public void render() {
		//clears the texture with black
		//glClearColor(); //sets the color to clear to
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//triangle red
		glPushMatrix(); //save rotation + translation matrix
		glTranslatef(0.3f, 0, 0); //move to the right
//		glMultMatrixf(); //multiply with matrix
		glRotatef(System.nanoTime() / 1_000_000_000f * 360 / 4f, 0, 0, 1); //rotate angle around the z axis (into the screen)
		
		glColor3f(1, 0, 0); //set color to draw
		glBegin(GL_TRIANGLES); //begin drawing triangles
		glVertex2f(-0.5f, -0.5f); //first corner
		glVertex2f(0.5f, -0.5f); //secound corner
		glVertex2f(0f, 0.5f); //third corner
		glVertex2f(-0.5f, 0.5f); //first corner
		glVertex2f(0.5f, 0.5f); //secound corner
		glVertex2f(0f, 0f); //third corner
		glEnd(); //end drawing
		
		glPopMatrix(); //restore matrix
		
		//triangle green
		glPushMatrix();
		glTranslatef(-0.3f, 0, 0);
		
		glColor3f(0, 1, 0);
		glBegin(GL_TRIANGLES);
		glVertex2f(-0.5f, -0.5f);
		glVertex2f(0.5f, -0.5f);
		glVertex2f(0f, 0.5f);
		glEnd();
		
		glPopMatrix();
	}
	
}
