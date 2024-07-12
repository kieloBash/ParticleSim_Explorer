package org.example.ui;

import org.example.utils.Window;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player {
    private float x, y;
    private float speed;
    private boolean visible;
    float radius = 0.02f;
    private static final float[] DEFAULT_COLOR = {1.0f, 0.0f, 0.0f};

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = 0.4f;
        this.visible = true;
    }

    public void update(float dt) {
        // Handle player movement here (e.g., WASD and arrow key controls)
        if ((y + radius < 0.9f) && glfwGetKey(Window.getGLFWWindow(), GLFW_KEY_W) == GLFW_PRESS || glfwGetKey(Window.getGLFWWindow(), GLFW_KEY_UP) == GLFW_PRESS ) {
            y += speed * dt;
        }
        if ((x + radius > -0.91f) && glfwGetKey(Window.getGLFWWindow(), GLFW_KEY_A) == GLFW_PRESS || glfwGetKey(Window.getGLFWWindow(), GLFW_KEY_LEFT) == GLFW_PRESS) {
            x -= speed * dt;
        }
        if ((y + radius > -0.86f) && glfwGetKey(Window.getGLFWWindow(), GLFW_KEY_S) == GLFW_PRESS || glfwGetKey(Window.getGLFWWindow(), GLFW_KEY_DOWN) == GLFW_PRESS) {
            y -= speed * dt;
        }
        if ((x + radius < 0.95f) && glfwGetKey(Window.getGLFWWindow(), GLFW_KEY_D) == GLFW_PRESS || glfwGetKey(Window.getGLFWWindow(), GLFW_KEY_RIGHT) == GLFW_PRESS) {
            x += speed * dt;
        }

    }

    public void render() {
        if (!visible) return;


        float aspectRatio = (float) Window.getHeight() / Window.getWidth();
        float radiusX = radius * aspectRatio;
        float radiusY = radius;

        glColor3f(DEFAULT_COLOR[0], DEFAULT_COLOR[1], DEFAULT_COLOR[2]);
        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(x, y); // Center of the circle
        for (int i = 0; i <= 100; i++) {
            double angle = 2.0 * Math.PI * i / 100;
            float dx = radiusX * (float) Math.cos(angle);
            float dy = radiusY * (float) Math.sin(angle);
            glVertex2f(x + dx, y + dy);
        }
        glEnd();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
