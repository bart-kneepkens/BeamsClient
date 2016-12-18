/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.UserInterface;
import beamsClient.BeamsClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.DisplayManager;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class SettingsWindow extends Window {

    public SettingsWindow(UserInterface userInterface) throws IOException {
        super(userInterface);

        Button btnResetScene = new Button(40, 40, new Vector2f(150, 25), 1);
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

        Button btnResetUI = new Button(40, 40, new Vector2f(210, 25), 1);
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

        Checkbox cboxAntiAliasing = new Checkbox(17, 17, new Vector2f(40, 400 - 25), 1);
        cboxAntiAliasing.subscribe(MouseInput.getMouseSubject());
        cboxAntiAliasing.onClick(x -> this.cboxAntiAliasing_Toggle(x));
        this.addChild(cboxAntiAliasing);

    }

    private void btnResetScene_Click(Event event) throws IOException {
        BeamsClient.loadDefaultScene();
        System.out.println("Resetted scene!");
    }

    private void btnResetUI_Click(Event event) throws IOException {
        BeamsClient.loadDefaultUserInterface();
        System.out.println("Resetted user interface!");
    }
    
    private void cboxAntiAliasing_Toggle(Event event){
        Checkbox cbox = (Checkbox) event.getSender();
    }

}
