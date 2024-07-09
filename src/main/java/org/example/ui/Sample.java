package org.example.ui;

import imgui.*;
import imgui.enums.ImGuiColorEditFlags;
import imgui.enums.ImGuiCond;
import imgui.enums.ImGuiInputTextFlags;
import org.example.utils.Window;

public class Sample {
    final float[] backgroundColor = new float[]{1, 1, 1};

    private final ImBool showDemoWindow = new ImBool();

    private float[] ballColor = new float[]{0, 0, 1};
    private ImInt numBallsToSpawn = new ImInt(1);

    //SINGLES
    private ImFloat posX = new ImFloat(0f);
    private ImFloat posY = new ImFloat(0f);
    private ImFloat velocity = new ImFloat(0f);
    private ImFloat angle = new ImFloat(0f);

    //DISTANCE MODE
    private ImFloat start_posX = new ImFloat(0f);
    private ImFloat start_posY = new ImFloat(0f);
    private ImFloat end_posX = new ImFloat(0f);
    private ImFloat end_posY = new ImFloat(0f);

    //SPEED MODE
    private ImFloat start_vel = new ImFloat(0f);
    private ImFloat end_vel = new ImFloat(0f);

    //ANGLE MODE
    private ImFloat start_angle = new ImFloat(0f);
    private ImFloat end_angle = new ImFloat(0f);

    private float INPUT_SIZE = 100;


    public void render() {
        ImGui.setNextWindowSize(420, 330, ImGuiCond.Once);
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Once);

        ImGui.begin("Settings Window");  // Start Custom window

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
        ImGui.setNextItemWidth(INPUT_SIZE); // Set the width of the input field
        ImGui.inputInt("##numBallsToSpawn", numBallsToSpawn, 1000);

        ImGui.text("Particle color:");
        ImGui.sameLine();
        ImGui.colorEdit3("##ballColor", ballColor, ImGuiColorEditFlags.NoInputs | ImGuiColorEditFlags.NoDragDrop);

        ImGui.separator();
        ImGui.text("Current Mode: ");
        ImGui.sameLine();
        ImGui.text("" + Window.getSpawnMode());

        if (ImGui.button("Distance")) {
            System.out.println("Distance Mode");
            Window.setSpawnMode(Window.SpawnMode.DISTANCE_MODE);
        }
        ImGui.sameLine();
        if (ImGui.button("Speed")) {
            System.out.println("Speed Mode");
            Window.setSpawnMode(Window.SpawnMode.SPEED_MODE);
        }
        ImGui.sameLine();
        if (ImGui.button("Angle")) {
            System.out.println("Angle Mode");
            Window.setSpawnMode(Window.SpawnMode.ANGLE_MODE);
        }

        ImGui.separator();

        displaySpawnMode();

        ImGui.separator();
        ImGui.text("Particle Count: ");
        ImGui.sameLine();
        ImGui.text("" + Window.getBalls().size());
        ImGui.text("FPS: ");
        ImGui.sameLine();
        ImGui.text("" + Window.getFPS());
        ImGui.text("View Mode: ");
        ImGui.sameLine();
        ImGui.text("" + Window.getViewMode());

        if(Window.getViewMode() == Window.ViewMode.DEVELOPER){
            if (ImGui.button("Switch to Explorer")) {
                Window.setViewMode(Window.ViewMode.EXPLORER);
            }
        }else{
            if (ImGui.button("Switch to Developer")) {
                Window.setViewMode(Window.ViewMode.DEVELOPER);
            }
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

    private void displaySpawnMode(){
        Window.SpawnMode currentMode = Window.getSpawnMode();
        switch (currentMode){
            case DISTANCE_MODE -> {
                ImGui.text("Start X:  ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##start_posX",start_posX);
                ImGui.sameLine();
                ImGui.text("Start Y:  ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##start_posY",start_posY);

                ImGui.text("End X:    ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##end_posX",end_posX);
                ImGui.sameLine();
                ImGui.text("End Y:    ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##end_posY",end_posY);


                ImGui.text("Velocity: ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##velocity",velocity);
                ImGui.sameLine();
                ImGui.text("Angle:    ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##angle",angle);

                //BUTTON SPAWN
                if (ImGui.button("Spawn")) {
                    Window.spawnDistanceBalls(numBallsToSpawn.get(),start_posX.get(),end_posX.get(),start_posY.get(),end_posY.get(),velocity.get()/10,angle.get(),ballColor);
                }
            }
            case SPEED_MODE -> {
                ImGui.text("Start Velocity:");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##start_vel",start_vel);
                ImGui.sameLine();
                ImGui.text("End Velocity:  ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##end_vel",end_vel);


                ImGui.text("Position X:    ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##posX",posX);
                ImGui.sameLine();
                ImGui.text("Position Y:    ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##posY",posY);

                ImGui.text("Angle:         ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##angle",angle);

                //BUTTON SPAWN
                if (ImGui.button("Spawn")) {
                    Window.spawnSpeedBalls(numBallsToSpawn.get(),start_vel.get(),end_vel.get(),posX.get(),posY.get(),angle.get(),ballColor);
                }
            }
            case ANGLE_MODE -> {
                ImGui.text("Start Angle:");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##start_angle",start_angle);
                ImGui.sameLine();
                ImGui.text("End Angle:  ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##end_angle",end_angle);


                ImGui.text("Position X: ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##posX",posX);
                ImGui.sameLine();
                ImGui.text("Position Y: ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##posY",posY);

                ImGui.text("Velocity:   ");
                ImGui.sameLine();
                ImGui.setNextItemWidth(INPUT_SIZE);
                ImGui.inputFloat("##velocity",velocity);

                //BUTTON SPAWN
                if (ImGui.button("Spawn")) {
                    Window.spawnAngleBalls(numBallsToSpawn.get(),start_angle.get(),end_angle.get(),posX.get(),posY.get(),velocity.get(),ballColor);
                }
            }
        }
    }

    public float[] getBackgroundColor(){
        return backgroundColor;
    }
}
