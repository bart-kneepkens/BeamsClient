/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.objects;

import gui.UserInterface;
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
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class SettingsWindow extends Window {

    private JDialog dialog;

    public SettingsWindow(UserInterface userInterface) throws IOException {
        super(userInterface);

        dialog = new JDialog();

        Button btnResetScene = new Button(userInterface, 40, 40, new Vector2f(200, 370), 1);
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

        Button btnResetUI = new Button(userInterface, 40, 40, new Vector2f(120, 370), 1);
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

        Button btnLoadTerrain = new Button(userInterface, 40, 40, new Vector2f(40, 370), 1);
        btnLoadTerrain.loadTextureAtlas("buttons/buttonTerrain_Atlas");
        btnLoadTerrain.subscribe(MouseInput.getMouseSubject());
        btnLoadTerrain.onClick(x
                -> this.btnLoadTerrain_Click(x));
        this.addChild(btnLoadTerrain);

        Checkbox cboxAntiAliasing = new Checkbox(userInterface, 20, 20, new Vector2f(40, 200), 1);
        cboxAntiAliasing.subscribe(MouseInput.getMouseSubject());
        cboxAntiAliasing.setLabel("Anti-Aliasing");
        this.addChild(cboxAntiAliasing);

        Checkbox cboxAnisoTropic = new Checkbox(userInterface, 20, 20, new Vector2f(40, 230), 1);
        cboxAnisoTropic.subscribe(MouseInput.getMouseSubject());
        cboxAnisoTropic.setLabel("Anisotropic Filtering");
        this.addChild(cboxAnisoTropic);

        Checkbox cboxDayNight = new Checkbox(userInterface, 20, 20, new Vector2f(40, 260), 1);
        cboxDayNight.subscribe(MouseInput.getMouseSubject());
        cboxDayNight.onClick(x -> this.cboxDayNight_Toggle(x));
        cboxDayNight.setLabel("Day Mode");
        if (DisplayManager.white) {
            cboxDayNight.setChecked(true);
        }
        this.addChild(cboxDayNight);

    }

    private void btnLoadTerrain_Click(Event event) {
        Mouse.setGrabbed(false);

        FileDialog fd = new FileDialog(dialog, "Choose a file", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFilenameFilter((File dir, String name) -> name.endsWith(".ter"));
        fd.setVisible(true);

        String filename = fd.getDirectory() + fd.getFile();
        if (fd.getFile() == null) {
            System.out.println("You cancelled the choice");
        } else {
            try {
                BeamsClient.getInstance().getScene().setTerrain(FileLoader.loadTerrain(new File(filename)));
            } catch (IOException ex) {
                dialog.dispose();
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dialog.dispose();
    }

    private void btnResetScene_Click(Event event) throws IOException {
        BeamsClient.getInstance().loadDefaultScene();
        System.out.println("Resetted scene!");
//        Runtime runtime = Runtime.getRuntime();
//        String[] args = {"osascript", "-e", "set a to \"java\"\n"
//            + "set bid to id of application a\n"
//            + "tell application a\n"
//            + "	reopen -- open a new default window if there are no windows\n"
//            + "	activate -- make frontmost\n"
//            + "end tell\n"
//            + "tell application \"System Events\" to tell (process 1 where bundle identifier is bid)\n"
//            + "	click (button 1 of window 1 where subrole is \"AXFullScreenButton\")\n"
//            + "end tell"};
//        Process process = runtime.exec(args);
    }

    private void btnResetUI_Click(Event event) throws IOException {
        BeamsClient.getInstance().loadDefaultUserInterface();
        System.out.println("Resetted user interface!");
    }

    private void cboxDayNight_Toggle(Event event) {

        Checkbox cbox = (Checkbox) event.getSender();
        if (cbox.isChecked()) {
            DisplayManager.white = true;
            BeamsClient.getInstance().getScene().getSun().getLight().setColour(new Vector3f(1f, 1f, 0.9f));
            BeamsClient.getInstance().getScene().getSun().getLight().setAttenuation(new Vector3f(0.5f, 0, 0));
        } else {
            DisplayManager.white = false;
            BeamsClient.getInstance().getScene().getSun().getLight().setColour(new Vector3f(1, 1, 1));
            BeamsClient.getInstance().getScene().getSun().getLight().setAttenuation(new Vector3f(1f, 0, 0));
        }
    }

}
