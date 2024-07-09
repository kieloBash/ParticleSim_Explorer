package org.example.utils;

import org.example.ui.Ball;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private int width, height;
    private String title;

    private long glfwWindow;
    private static Window window = null;
    private ImGUILayer imGUILayer;

    private ArrayList<Ball> balls = new ArrayList<>();

    //FPS
    private float frameTime = 0.0f;
    private int fpsCounter = 0;
    private float lastTime = 0.0f;
    private int fps = 0;

    private Window() {
        this.width = 1280;
        this.height = 720;
        this.title = "Particle Sim v2";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (glfwWindow == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // Make the OpenGl context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
        this.imGUILayer = new ImGUILayer(glfwWindow);
        this.imGUILayer.initImGui();
    }

    public void loop() {
        float beginTime = 0.0f;
        float endTime = 0.0f;
        float dt = -1.0f;


        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            float[] bgColor = imGUILayer.getUI().getBackgroundColor();
            glClearColor(bgColor[0], bgColor[1], bgColor[2], 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            Ball newBall = imGUILayer.getUI().getNewBall();
            if (newBall != null) {
                balls.add(newBall);
            }

            for (int i = 0; i < balls.size(); i++) {
                Ball currBall = balls.get(i);
                currBall.update(dt, width, height);
                currBall.render();
            }


            this.imGUILayer.update(dt);
            glfwSwapBuffers(glfwWindow);

            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;

            // Update FPS calculation
            frameTime += dt;
            fpsCounter++;

            if (frameTime >= 1.0f) {
                fps = fpsCounter;
                fpsCounter = 0;
                frameTime -= 1.0f;
            }
        }
    }

    public static int getWidth() {
        return get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void clearBalls(){
        get().balls.clear();
    }

    public static void createBalls(int count, float posX, float posY, float velX, float velY, float[] ballColor) {
        float canvasPosX = (posX / getWidth()) * 2 - 1;
        float canvasPosY = (posY / getHeight()) * 2 - 1;

        // Create balls with canvas coordinates
        for (int i = 0; i < count; i++) {
            Ball newBall = new Ball(canvasPosX, canvasPosY, 0.02f, velX, velY, 10, ballColor);
            get().balls.add(newBall);
        }
    }

    public static ArrayList<Ball> getBalls(){
        return get().balls;
    }

    public static int getFPS() {
        return get().fps;
    }


}
