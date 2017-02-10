/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import beamsClient.BeamsClient;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Blackened
 */
public class KeyboardInput {

    public static void checkInputs() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    BeamsClient.getInstance().exit();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_1) {
                    BeamsClient.getInstance().getScene().getPlayer().fireBullet();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_2) {
                    BeamsClient.getInstance().getScene().getPlayer().fireHalo();
                }
            }
        }
    }

}
