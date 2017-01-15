/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.UserInterface;
import beamsClient.BeamsClient;
import dataAccess.FileLoader;
import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import toolbox.Settings;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class SettingsWindow extends Window {

    public SettingsWindow(UserInterface userInterface) throws IOException {
        super(userInterface);

        Button btnResetScene = new Button(userInterface, 40, 40, new Vector2f(150, this.getGUIElement().getHeight() - 50), 1);
        btnResetScene.loadTextureAtlas("buttons/buttonReset_Atlas");
        btnResetScene.subscribe(MouseInput.getMouseSubject());
        btnResetScene.onClick(x -> {
            try {
                this.btnResetScene_Click(x);
            } catch (IOException ex) {
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.addChild(btnResetScene);

        Button btnResetUI = new Button(userInterface, 40, 40, new Vector2f(210, this.getGUIElement().getHeight() - 50), 1);
        btnResetUI.loadTextureAtlas("buttons/buttonReset_Atlas");
        btnResetUI.subscribe(MouseInput.getMouseSubject());
        btnResetUI.onClick(x -> {
            try {
                this.btnResetUI_Click(x);
            } catch (IOException ex) {
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.addChild(btnResetUI);
        
        Button btnLoadTerrain = new Button(userInterface, 40, 40, new Vector2f(100, this.getGUIElement().getHeight() - 50), 1);
        btnLoadTerrain.loadTextureAtlas("buttons/buttonTerrain_Atlas");
        btnLoadTerrain.subscribe(MouseInput.getMouseSubject());
        btnLoadTerrain.onClick(x -> 
                this.btnLoadTerrain_Click(x));
        this.addChild(btnLoadTerrain);

        Checkbox cboxAntiAliasing = new Checkbox(userInterface, 20, 20, new Vector2f(200, 300), 1);
        cboxAntiAliasing.subscribe(MouseInput.getMouseSubject());
        cboxAntiAliasing.onClick(x -> {
            try {
                this.cboxAntiAliasing_Toggle(x);
            } catch (IOException ex) {
                Logger.getLogger(SettingsWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cboxAntiAliasing.setLabel("Anisotropic filtering");
        this.addChild(cboxAntiAliasing);

    }

    private void btnLoadTerrain_Click(Event event){
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
    
    private void btnResetScene_Click(Event event) throws IOException {
        BeamsClient.loadDefaultScene();
        System.out.println("Resetted scene!");
    }

    private void btnResetUI_Click(Event event) throws IOException {
        BeamsClient.loadDefaultUserInterface();
        System.out.println("Resetted user interface!");
    }
    
    private void cboxAntiAliasing_Toggle(Event event) throws IOException{
//        Checkbox cbox = (Checkbox) event.getSender();
//        if (cbox.isChecked()){
//            Settings.ANISOTROPIC_FILTERING = true;
//        }
//        else{
//            Settings.ANISOTROPIC_FILTERING = false;
//        }
//        BeamsClient.loadDefaultScene();
//        BeamsClient.loadDefaultUserInterface();
//        
//        System.out.println("Resetted scene!");
    }

}
