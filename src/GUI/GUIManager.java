/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DataAccess.FileLoader;
import GUI.objects.Button;
import GUI.objects.InvisibleListener;
import beamsClient.BeamsClient;
import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class GUIManager {

    private final GUI gui;

    public GUIManager() {

        this.gui = new GUI();

        //<editor-fold defaultstate="collapsed" desc="Buttons">
        // Button 1
        Button buttonExit = new Button(30, 30, new Vector2f(10, Display.getHeight() - 40));
        buttonExit.load();
        buttonExit.onClick(x -> this.buttonExit_Click(x));
        buttonExit.loadMainTexture("buttons/exit_main");
        buttonExit.loadHoverTexture("buttons/exit_hover");
        buttonExit.loadClickTexture("buttons/exit_click");
        buttonExit.subscribe(MouseInput.getMouseSubject());
        this.gui.addElement(buttonExit.getGUIElement(), 0);

        Button buttonLoadTerrain = new Button(30, 30, new Vector2f(50, Display.getHeight() - 40));
        buttonLoadTerrain.load();
        buttonLoadTerrain.onClick(x -> this.buttonLoadTerrain_Click(x));
        buttonLoadTerrain.loadMainTexture("buttons/camera_main");
        buttonLoadTerrain.loadHoverTexture("buttons/camera_hover");
        buttonLoadTerrain.loadClickTexture("buttons/camera_click");
        buttonLoadTerrain.subscribe(MouseInput.getMouseSubject());
        this.gui.addElement(buttonLoadTerrain.getGUIElement(), 0);

        //</editor-fold>
        
        InvisibleListener listener = new InvisibleListener(Display.getWidth() - 2, Display.getHeight() - 2, new Vector2f(1, 1), new Vector3f(0,0,0));
        listener.onPress(x -> this.listener_Press(x));
        listener.subscribe(MouseInput.getMouseSubject());
    }

    /**
     * Returns all elements of the GUI that need to be rendered.
     *
     * @return
     */
    public GUI getGUI() {
        return this.gui;
    }

    private void buttonExit_Click(Event event) {
        BeamsClient.keepRunning = false;
    }

    private void buttonLoadTerrain_Click(Event event) {
        JDialog dialog = new JDialog();
        FileDialog fd = new FileDialog(dialog, "Choose a file", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFilenameFilter((File dir, String name) -> name.endsWith(".ter"));
        fd.setVisible(true);

        String filename = fd.getDirectory() + fd.getFile();
        if (fd.getFile() == null) {
            System.out.println("You cancelled the choice");
        } else {
            try {
                BeamsClient.scene.setTerrain(FileLoader.loadTerrain(new File(filename)));
            } catch (IOException ex) {
                dialog.dispose();
                Logger.getLogger(GUIManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dialog.dispose();
    }
    
    private void listener_Press(Event event){
        if (event.getMouseState().isButton1Down()) {
            BeamsClient.scene.getCamera().turnHorizontally((float)event.getMouseState().getdX() / 8f);
            BeamsClient.scene.getCamera().turnVertically((float)event.getMouseState().getdY() / -8f);
           
        }
    }

}
