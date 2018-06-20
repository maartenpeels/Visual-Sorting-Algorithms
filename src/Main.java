import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import enums.Algorithms;
import helpers.Logger;
import helpers.SortingArray;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

public class Main implements Runnable{
    private int width = 1280;
    private int height = 720;

    private Thread thread;
    private boolean running = false;

    private long window;

    private SortingArray array;

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        array = new SortingArray(40);
        array.setAlgorithm(Algorithms.RADIXSORT);

        running = true;
        thread = new Thread(this, "Visual Sorting");
        thread.run();
    }

    private void init() {
        if(!glfwInit()) {
            //Failed
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        window = glfwCreateWindow(width, height, "Sorting", NULL, NULL);

        try (MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
                            );
        }

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();

        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        glEnable(GL_DEPTH_TEST);
    }

    public void run() {
        Logger.Log("Thread started!");
        init();
        while(running) {
            update();
            render();

            if(glfwWindowShouldClose(window))
                running = false;
        }
    }

    private void update() {
        glfwPollEvents();

        array.step();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        array.draw(width, height);

        glfwSwapBuffers(window);
    }
}
