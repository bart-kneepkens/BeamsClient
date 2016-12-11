/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.objects.Button;
import GUI.objects.Panel;
import beamsClient.BeamsClient;
import dataAccess.FileLoader;
import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class UserInterface {

    private final GUI gui;

    public UserInterface() throws IOException {

        this.gui = new GUI();

        //<editor-fold defaultstate="collapsed" desc="Buttons">
        // Button 1
        Button buttonExit = new Button(50, 50, new Vector2f(Display.getWidth() / 2 - 100, 15), 0);
        buttonExit.loadTextureAtlas("buttons/buttonExit_Atlas");
        buttonExit.load();
        buttonExit.subscribe(MouseInput.getMouseSubject());
        buttonExit.onClick(x -> this.buttonExit_Click(x));
        this.gui.addElement(buttonExit.getGUIElement());

        Button buttonLoadTerrain = new Button(50, 50, new Vector2f(Display.getWidth() / 2 - 25, 15), 0);
        buttonLoadTerrain.loadTextureAtlas("buttons/buttonTerrain_Atlas");
        buttonLoadTerrain.load();
        buttonLoadTerrain.subscribe(MouseInput.getMouseSubject());
        buttonLoadTerrain.onClick(x -> this.buttonLoadTerrain_Click(x));
        this.gui.addElement(buttonLoadTerrain.getGUIElement());  
        
        Button buttonResetAll = new Button(50, 50, new Vector2f(Display.getWidth() / 2 + 50, 15), 0);
        buttonResetAll.loadTextureAtlas("buttons/buttonReset_Atlas");
        buttonResetAll.load();
        buttonResetAll.subscribe(MouseInput.getMouseSubject());
        buttonResetAll.onClick(x -> {
            try {
                this.buttonResetAll_Click(x);
            } catch (IOException ex) {
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.gui.addElement(buttonResetAll.getGUIElement());  
        
        Panel panel = new Panel(250, 250, new Vector2f(Display.getWidth() / 2 - 125,-160), 1);
        panel.loadTexture("panel");
        panel.load();
        this.gui.addElement(panel.getGUIElement());
        
//        Panel panel2 = new Panel(100, 100, new Vector2f(Display.getWidth() / 2 - 150,0), 1);
//        panel2.loadTexture("vine2");
//        panel2.load();
//        this.gui.addElement(panel2.getGUIElement());
        
        //</editor-fold>
        
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
        BeamsClient.exit();
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
                BeamsClient.getScene().setTerrain(FileLoader.loadTerrain(new File(filename)));
            } catch (IOException ex) {
                dialog.dispose();
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dialog.dispose();
    }
    
    private void buttonResetAll_Click(Event event) throws IOException{
        BeamsClient.loadDefaultScene();
        BeamsClient.loadDefaultUserInterface();
        Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
    }
}
