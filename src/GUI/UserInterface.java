/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.objects.Button;
import GUI.objects.Container;
import GUI.objects.Label;
import GUI.objects.Window;
import GUI.objects.Panel;
import GUI.objects.SettingsWindow;
import beamsClient.BeamsClient;
import dataAccess.lwjgl.Loader;
import fontMeshCreator.FontType;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import toolbox.Autonomous;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class UserInterface implements Autonomous {

    private final GUI gui;

    private boolean active = true;

    public UserInterface() throws IOException {
        this.gui = new GUI();

        //<editor-fold defaultstate="collapsed" desc="Buttons">
        Button buttonExit = new Button(50, 50, new Vector2f(0, 0), 0);
        buttonExit.loadTextureAtlas("buttons/buttonExit_Atlas");
        buttonExit.subscribe(MouseInput.getMouseSubject());
        buttonExit.onClick(x -> this.buttonExit_Click(x));

        Button buttonLoadTerrain = new Button(50, 50, new Vector2f(75, 0), 0);
        buttonLoadTerrain.loadTextureAtlas("buttons/buttonSettings_Atlas");
        buttonLoadTerrain.subscribe(MouseInput.getMouseSubject());
        buttonLoadTerrain.onClick(x -> {
            try {
                this.buttonLoadTerrain_Click(x);
            } catch (IOException ex) {
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Panel panel = new Panel(250, 250, new Vector2f(-25, -170), 1);
        panel.loadTexture("window");

        Container container = new Container(200, 50, new Vector2f(Display.getWidth() / 2 - 100, 20), 0, this);
        container.addChild(buttonExit, true);
        container.addChild(buttonLoadTerrain, true);
        container.addChild(panel, true);
        container.load();
        container.setRendered(true);
        gui.addElement(container);
        //</editor-fold>
    }

    public GUI getGui() {
        return gui;
    }

    @Override
    public void update() {
        this.gui.getAutonomousElements().forEach(x -> x.update());
    }

    private void buttonExit_Click(Event event) {
        BeamsClient.exit();
    }

    private void buttonLoadTerrain_Click(Event event) throws IOException {
//        Mouse.setGrabbed(false);
//        JDialog dialog = new JDialog();
//        FileDialog fd = new FileDialog(dialog, "Choose a file", FileDialog.LOAD);
//        fd.setDirectory("C:\\");
//        fd.setFilenameFilter((File dir, String name) -> name.endsWith(".ter"));
//        fd.setVisible(true);
//
//        String filename = fd.getDirectory() + fd.getFile();
//        if (fd.getFile() == null) {
//            System.out.println("You cancelled the choice");
//        } else {
//            try {
//                BeamsClient.getScene().setTerrain(FileLoader.loadTerrain(new File(filename)));
//            } catch (IOException ex) {
//                dialog.dispose();
//                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        dialog.dispose();

        this.openWindow(new SettingsWindow(this).loadWindow());
    }

    public void openWindow(Window window) {
        this.gui.addElement(window);
    }

    public void closeWindow(Window window) {
        
        this.gui.removeElement(window);
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean value) {
        this.active = value;
    }
}
