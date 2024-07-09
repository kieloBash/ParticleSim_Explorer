package org.example.ui;
import org.example.utils.Window;

import static org.lwjgl.opengl.GL11.*;

public class Ball {
    private float x, y;
    private float radius;
    private float velX, velY;
    private int numSegments;
    private float[] color;

    private static final float[] DEFAULT_COLOR = {0.0f, 0.0f, 1.0f};

    public Ball(float x, float y, float radius, float velX, float velY, int numSegments, float[] color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velX = velX;
        this.velY = velY;
        this.numSegments = numSegments;
        this.color = color;
    }

    public void update(float dt, int windowWidth, int windowHeight) {
        // Update position
        x += velX * dt;
        y += velY * dt;

        // Check for collisions with window borders and bounce
        if (x - radius < -1.0f) {
            x = -1.0f + radius;
            velX = -velX;
        } else if (x + radius > 1.0f) {
            x = 1.0f - radius;
            velX = -velX;
        }

        if (y - radius < -1.0f) {
            y = -1.0f + radius;
            velY = -velY;
        } else if (y + radius > 1.0f) {
            y = 1.0f - radius;
            velY = -velY;
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

}
