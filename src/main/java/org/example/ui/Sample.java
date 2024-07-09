package org.example.ui;

import imgui.ImBool;
import imgui.ImGui;
import imgui.ImString;
import imgui.ImVec2;
import imgui.enums.ImGuiColorEditFlags;
import imgui.enums.ImGuiCond;
import imgui.enums.ImGuiInputTextFlags;
import org.lwjgl.BufferUtils;

public class Sample {
    // Test data for payload
    private final byte[] testPayload = "Test Payload".getBytes();
    private String dropTargetText = "Drop Here";

    final float[] backgroundColor = new float[]{0.5f, 0, 0};

    private final ImString resizableStr = new ImString(5);
    private final ImBool showDemoWindow = new ImBool();

    public void render() {
        ImGui.setNextWindowSize(600, 300, ImGuiCond.Once);
        ImGui.setNextWindowPos(10, 10, ImGuiCond.Once);

        ImGui.begin("Custom window");  // Start Custom window


        // Checkbox to show demo window
        ImGui.checkbox("Show demo window", showDemoWindow);

        ImGui.separator();

        // Drag'n'Drop functionality
        ImGui.button("Drag me");
        if (ImGui.beginDragDropSource()) {
            ImGui.setDragDropPayload("payload_type", testPayload, testPayload.length);
            ImGui.text("Drag started");
            ImGui.endDragDropSource();
        }
        ImGui.sameLine();
        ImGui.text(dropTargetText);
        if (ImGui.beginDragDropTarget()) {
            final byte[] payload = ImGui.acceptDragDropPayload("payload_type");
            if (payload != null) {
                dropTargetText = new String(payload);
            }
            ImGui.endDragDropTarget();
        }

        // Color picker
        ImGui.alignTextToFramePadding();
        ImGui.text("Background color:");
        ImGui.sameLine();
        ImGui.colorEdit3("##click_counter_col", backgroundColor, ImGuiColorEditFlags.NoInputs | ImGuiColorEditFlags.NoDragDrop);

        ImGui.separator();

        // Input field with auto-resize ability
        ImGui.text("You can use text inputs with auto-resizable strings!");
        ImGui.inputText("Resizable input", resizableStr, ImGuiInputTextFlags.CallbackResize);
        ImGui.text("text len:");
        ImGui.sameLine();
        ImGui.textColored(.12f, .6f, 1, 1, Integer.toString(resizableStr.getLength()));
        ImGui.sameLine();
        ImGui.text("| buffer size:");
        ImGui.sameLine();
        ImGui.textColored(1, .6f, 0, 1, Integer.toString(resizableStr.getBufferSize()));

        ImGui.separator();
        ImGui.newLine();

        if (ImGui.button("Click Me")) {
            System.out.println("Button clicked!");
        }
        ImGui.end();  // End Custom window

        if (showDemoWindow.get()) {
            ImGui.showDemoWindow(showDemoWindow);
        }
    }
}
