package org.example.ui;

import org.example.utils.Window;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;

public class Ball {
    private float x, y;
    private float radius;
    private float velocity, angle;
    private int numSegments;
    private float[] color;

    private static final float[] DEFAULT_COLOR = {0.0f, 0.0f, 1.0f};

    public Ball(float x, float y, float radius, float velocity, float angle, int numSegments, float[] color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocity = velocity;
        this.angle = angle;
        this.numSegments = numSegments;
        this.color = Arrays.copyOf(color, color.length); // Create a copy of the color array
        if (this.color.length < 3) {
            this.color = DEFAULT_COLOR; // Fallback to default if array length is incorrect
        }
    }

    public void update(float dt) {
        double velocityPerUpdate = velocity * dt;

        // Update position
        x += (float) (velocityPerUpdate * Math.cos(Math.toRadians(angle)));
        y += (float) (velocityPerUpdate * Math.sin(Math.toRadians(angle)));

        // Check for collisions with window borders and bounce
        if (x - radius < -1.0f) {
            x = -1.0f + radius;
            angle = (int) (180 - angle);
        } else if (x + radius > 1.0f) {
            x = 1.0f - radius;
            angle = (int) (180 - angle);
        }

        if (y - radius < -1.0f) {
            y = -1.0f + radius;
            angle = (int) (360 - angle);
        } else if (y + radius > 1.0f) {
            y = 1.0f - radius;
            angle = (int) (360 - angle);
        }
    }

    public void render() {
        float aspectRatio = (float) Window.getHeight() / Window.getWidth();
        float radiusX = radius * aspectRatio;
        float radiusY = radius;

        glColor3f(color[0], color[1], color[2]); // Set color to blue
        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(x, y); // Center of the circle
        for (int i = 0; i <= numSegments; i++) {
            double angle = 2.0 * Math.PI * i / numSegments;
            float dx = radiusX * (float) Math.cos(angle);
            float dy = radiusY * (float) Math.sin(angle);
            glVertex2f(x + dx, y + dy);
        }
        glEnd();
    }

    // New methods to get the position of the ball
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
