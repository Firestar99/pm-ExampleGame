package pmExample.ExampleGame;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;

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
			windowPointer = glfwCreateWindow(800, 800, "Hi Window!", 0, 0);
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
	
	private int textureEmojiId;
	
	public void setup() {
		textureEmojiId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureEmojiId);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		try {
			PNGDecoder pngDecoder = new PNGDecoder(ExampleGame.class.getResourceAsStream("emoji.png"));
			int width = pngDecoder.getWidth();
			int height = pngDecoder.getHeight();
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
			pngDecoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void release() {
		glDeleteTextures(textureEmojiId);
	}
	
	public void update() {
	
	}
	
	public void render() {
		//clears the texture with black
		//glClearColor(); //sets the color to clear to
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//quad with emoji texture
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureEmojiId);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);
		glVertex2f(-1, -1);
		glTexCoord2f(0, 0);
		glVertex2f(-1, 1);
		glTexCoord2f(1, 0);
		glVertex2f(1, 1);
		glTexCoord2f(1, 1);
		glVertex2f(1, -1);
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisable(GL_TEXTURE_2D);
		
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
		glColor3f(1, 1, 1); //reset to white
		
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
		glColor3f(1, 1, 1); //reset to white
		
		glPopMatrix();
	}
	
	public void checkError() {
		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.out.println("GL ERROR: " + Integer.toHexString(error));
	}
	
}
