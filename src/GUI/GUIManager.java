/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.objects.Button;
import beamsClient.BeamsClient;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.Loader;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class GUIManager {

    private final GUI gui;
    private final Loader loader;

    public GUIManager(Loader loader) {

        this.gui = new GUI();
        this.loader = loader;

        //<editor-fold defaultstate="collapsed" desc="Buttons">
        
        // Button 1
        Button buttonExit = new Button(75, 75, new Vector2f(Display.getWidth() / 2 - 225, 50));
        buttonExit.load();
        buttonExit.onClick(x -> this.buttonExit_Click(x));
        buttonExit.loadMainTexture("buttons/exit_main");
        buttonExit.loadHoverTexture("buttons/exit_hover");
        buttonExit.loadClickTexture("buttons/exit_click");
        buttonExit.subscribe(MouseInput.getMouseSubject());
        this.gui.addElement(buttonExit.getGUIElement(), 0);

        Button buttonResetCamera = new Button(75, 75, new Vector2f(Display.getWidth() / 2 - 150, 50));
        buttonResetCamera.load();
        buttonResetCamera.onClick(x -> this.buttonResetCamera_Click(x));
        buttonResetCamera.loadMainTexture("buttons/camera_main");
        buttonResetCamera.loadHoverTexture("buttons/camera_hover");
        buttonResetCamera.loadClickTexture("buttons/camera_click");
        buttonResetCamera.subscribe(MouseInput.getMouseSubject());
        this.gui.addElement(buttonResetCamera.getGUIElement(), 0);

        //</editor-fold>
    }

    /**
     * Returns all elements of the GUI that need to be rendered.
     * @return 
     */
    public GUI getGUI() {
        return this.gui;
    }

    private void buttonExit_Click(Event event) {
        BeamsClient.keepRunning = false;
    }

    private void buttonResetCamera_Click(Event event) {
        BeamsClient.scene.resetCamera();
    }

}
