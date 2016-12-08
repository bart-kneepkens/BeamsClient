/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DataAccess.FileLoader;
import GUI.objects.Button;
import beamsClient.BeamsClient;
import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.DisplayManager;
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
}
