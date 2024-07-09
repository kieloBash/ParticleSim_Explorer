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
    public enum Mode {
        DISTANCE_MODE, SPEED_MODE, ANGLE_MODE
    }

    private int width, height;
    private String title;

    private long glfwWindow;
    private static Window window = null;
    private ImGUILayer imGUILayer;

    private ArrayList<Ball> balls = new ArrayList<>();
    private Mode spawnMode;

    //FPS
    private float frameTime = 0.0f;
    private int fpsCounter = 0;
    private int fps = 0;

    private Window() {
        this.width = 1280;
        this.height = 720;
        this.title = "Particle Sim v2";
        spawnMode = Mode.DISTANCE_MODE;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
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

            //Rendering the balls
            for (int i = 0; i < balls.size(); i++) {
                Ball currBall = balls.get(i);
                currBall.update(dt);
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

    public static void clearBalls() {
        get().balls.clear();
    }

    public static Mode getSpawnMode() {
        return get().spawnMode;
    }

    public static void setSpawnMode(Mode newMode) {
        get().spawnMode = newMode;
    }

    public static void spawnDistanceBalls(int n, float startX, float endX, float startY, float endY, float vel, float angle, float[] ballColor) {
        float deltaX = (endX - startX) / (n - 1);
        float deltaY = (endY - startY) / (n - 1);

        for (int i = 0; i < n; i++) {
            float compX = startX + (i * deltaX);
            float compY = startY + (i * deltaY);

            float x = (compX / getWidth()) * 2 - 1;
            float y = (compY / getWidth()) * 2 - 1;

            Ball newBall = new Ball(x, y, 0.02f, vel, angle, 10, ballColor);
            get().balls.add(newBall);
        }
    }

    public static void spawnSpeedBalls(int n, float startVel, float endVel, float posX, float posY, float angle, float[] ballColor) {
        double deltaVelocity = (endVel - startVel) / (n - 1);
        float x = (posX / getWidth()) * 2 - 1;
        float y = (posY / getWidth()) * 2 - 1;

        for (int i = 0; i < n; i++) {
            float velocity = (float) (startVel + i * deltaVelocity);

            Ball newBall = new Ball(x, y, 0.02f, velocity/10, angle, 10, ballColor);
            get().balls.add(newBall);
        }
    }

    public static void spawnAngleBalls(int n, float startAngle, float endAngle, float posX, float posY, float velocity, float[] ballColor) {
        double deltaAngle = (endAngle - startAngle) / (n - 1);

        float x = (posX / getWidth()) * 2 - 1;
        float y = (posY / getWidth()) * 2 - 1;

        for (int i = 0; i < n; i++) {
            float angle = (float)(startAngle + i * deltaAngle);

            Ball newBall = new Ball(x, y, 0.02f, velocity/10, angle, 10, ballColor);
            get().balls.add(newBall);
        }
    }

    public static ArrayList<Ball> getBalls() {
        return get().balls;
    }

    public static int getFPS() {
        return get().fps;
    }


}
