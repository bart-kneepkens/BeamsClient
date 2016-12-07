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
import org.lwjgl.input.Mouse;
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
        buttonExit.loadTextureAtlas("buttons/buttonExit_Atlas");
        buttonExit.load();
        buttonExit.subscribe(MouseInput.getMouseSubject());
        buttonExit.onClick(x -> this.buttonExit_Click(x));
        this.gui.addElement(buttonExit.getGUIElement(), 0);

        Button buttonLoadTerrain = new Button(30, 30, new Vector2f(50, Display.getHeight() - 40));
        buttonLoadTerrain.loadTextureAtlas("buttons/buttonTerrain_Atlas");
        buttonLoadTerrain.load();
        buttonLoadTerrain.subscribe(MouseInput.getMouseSubject());
        buttonLoadTerrain.onClick(x -> this.buttonLoadTerrain_Click(x));
        this.gui.addElement(buttonLoadTerrain.getGUIElement(), 0);

       
        //</editor-fold>
        
        InvisibleListener listener = new InvisibleListener(Display.getWidth() - 2, Display.getHeight() - 2, new Vector2f(1, 1), new Vector3f(0, 0, 0));
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
        Mouse.setGrabbed(false);
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
        if (event.getMouseState().isButton0Down() || event.getMouseState().isButton1Down()){
            Mouse.setGrabbed(true);
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
        }
        
    }
    
    private void listener_Hover(Event event){
        if(!event.getMouseState().isButton0Down() && !event.getMouseState().isButton1Down())
            Mouse.setGrabbed(false);
    }
}
