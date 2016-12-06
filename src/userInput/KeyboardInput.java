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
    
    public static void checkInputs(){
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            BeamsClient.keepRunning = false;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            BeamsClient.scene.getPlayer().moveForward();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            BeamsClient.scene.getPlayer().moveBackward();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            BeamsClient.scene.getPlayer().turnRight();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)){
            BeamsClient.scene.getPlayer().turnLeft();
        }
    }
    
}
