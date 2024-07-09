package org.example.ui;

import imgui.*;
import imgui.enums.ImGuiColorEditFlags;
import imgui.enums.ImGuiCond;
import imgui.enums.ImGuiInputTextFlags;
import org.example.utils.Window;

public class Sample {
    final float[] backgroundColor = new float[]{1, 1, 1};

    private final ImString resizableStr = new ImString(5);
    private final ImBool showDemoWindow = new ImBool();

    private float[] ballColor = new float[]{0, 0, 1};
    private ImInt numBallsToSpawn = new ImInt(1);
    private ImFloat posX = new ImFloat(0f);
    private ImFloat posY = new ImFloat(0f);
    private ImFloat velX = new ImFloat(0f);
    private ImFloat velY = new ImFloat(0f);
    private Ball newBall = null;



    public void render() {
        ImGui.setNextWindowSize(300, 300, ImGuiCond.Once);
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Once);

        ImGui.begin("Settings Window");  // Start Custom window


        // Checkbox to show demo window
//        ImGui.checkbox("Show demo window", showDemoWindow);

        ImGui.separator();

        // Color picker
        ImGui.alignTextToFramePadding();
        ImGui.text("Background color:");
        ImGui.sameLine();
        ImGui.colorEdit3("##click_counter_col", backgroundColor, ImGuiColorEditFlags.NoInputs | ImGuiColorEditFlags.NoDragDrop);

        ImGui.separator();

        // Ball creation controls
        ImGui.alignTextToFramePadding();
        ImGui.text("Number of Particles to Spawn:");
        ImGui.sameLine();
        ImGui.inputInt("##numBallsToSpawn", numBallsToSpawn);

        ImGui.text("Particle color:");
        ImGui.sameLine();
        ImGui.colorEdit3("##ballColor", ballColor, ImGuiColorEditFlags.NoInputs | ImGuiColorEditFlags.NoDragDrop);

        ImGui.text("Position X: ");
        ImGui.sameLine();
        ImGui.inputFloat("##posX",posX);
        ImGui.text("Position Y: ");
        ImGui.sameLine();
        ImGui.inputFloat("##posY",posY);

        ImGui.text("Velocity X: ");
        ImGui.sameLine();
        ImGui.inputFloat("##velX",velX);
        ImGui.text("Velocity Y: ");
        ImGui.sameLine();
        ImGui.inputFloat("##velY",velY);

        ImGui.separator();
        ImGui.text("Particle Count: ");
        ImGui.sameLine();
        ImGui.text("" + Window.getBalls().size());
        ImGui.text("FPS: ");
        ImGui.sameLine();
        ImGui.text("" + Window.getFPS());


        if (ImGui.button("Spawn Particle")) {
            Window.createBalls(numBallsToSpawn.get(),posX.get(), posY.get(), velX.get()/10,velY.get()/10,ballColor);
        }
        ImGui.sameLine();
        if (ImGui.button("Clear All Particles")) {
            Window.clearBalls();
        }

        ImGui.end();  // End Custom window

        if (showDemoWindow.get()) {
            ImGui.showDemoWindow(showDemoWindow);
        }
    }

    public float[] getBackgroundColor(){
        return backgroundColor;
    }

    public Ball getNewBall() {
        Ball ball = newBall;
        newBall = null; // Reset after retrieving
        return ball;
    }
}
