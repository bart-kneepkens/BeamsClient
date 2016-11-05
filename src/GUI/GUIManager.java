/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Listeners.Button;
import beamsclient.BeamsClient;
import entities.Camera;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import userInput.Event;

/**
 *
 * @author Blackened
 */
public class GUIManager {

    private List<Button> buttons;
    private Camera camera;

    public GUIManager(Camera camera) {
        this.buttons = new ArrayList<>();
        this.camera = camera;

        //<editor-fold defaultstate="collapsed" desc="Buttons">
        // Button 1
        Button newButton = new Button("button1", 50, 50, new Vector2f(Display.getWidth() / 2 - 100, 100), new Vector2f(0, 0));

        newButton.onClick(x -> this.button1_Click(x));
        newButton.load();
        newButton.loadMainTexture("exit_main");
        newButton.loadHoverTexture("exit_hover");
        newButton.loadClickTexture("exit_click");
        this.buttons.add(newButton);

        Button newButton2 = new Button("button2", 50, 50, new Vector2f(Display.getWidth() / 2 - 50, 100), new Vector2f(0, 0));

        newButton2.onClick(x -> this.button2_Click(x));
        newButton2.load();
        newButton2.loadMainTexture("camera_main");
        newButton2.loadHoverTexture("camera_hover");
        newButton2.loadClickTexture("camera_click");
        this.buttons.add(newButton2);
        
        Button newButton3 = new Button("button3", 50, 50, new Vector2f(Display.getWidth() / 2 - 0, 100), new Vector2f(0, 0));
        newButton3.load();
        newButton3.loadMainTexture("main");
        newButton3.loadHoverTexture("hover");
        newButton3.loadClickTexture("click");
        this.buttons.add(newButton3);
        
        Button newButton4 = new Button("button4", 50, 50, new Vector2f(Display.getWidth() / 2 + 50, 100), new Vector2f(0, 0));

        newButton4.load();
        newButton4.loadMainTexture("main");
        newButton4.loadHoverTexture("hover");
        newButton4.loadClickTexture("click");
        this.buttons.add(newButton4);
        
        Button newButton5 = new Button("button5", 50, 50, new Vector2f(Display.getWidth() / 2 + 100, 100), new Vector2f(0, 0));

        newButton5.load();
        newButton5.loadMainTexture("main");
        newButton5.loadHoverTexture("hover");
        newButton5.loadClickTexture("click");
        this.buttons.add(newButton5);

        //</editor-fold>
    }

    public List<Button> getAllButtons() {
        return this.buttons;
    }

    private void button1_Click(Event event) {
        BeamsClient.keepRunning = false;
    }

    private void button2_Click(Event event) {
        camera.reset();
    }

}
