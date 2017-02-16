/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import beamsClient.GameApplication;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Blackened
 */
public class ApplicationInputHandler {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private GameApplication application;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public ApplicationInputHandler(GameApplication application) {
        this.application = application;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void handleInput() {
        // Application keys
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            this.application.exit();
        }
    }
//</editor-fold>

}
