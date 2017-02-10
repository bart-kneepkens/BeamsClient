/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import beamsClient.BeamsClient;
import org.lwjgl.input.Keyboard;
import renderEngine.DisplayManager;

/**
 *
 * @author Blackened
 */
public class KeyboardInput {

    public static void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            BeamsClient.getInstance().exit();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F)){
            System.out.println("x: " + BeamsClient.getInstance().getScene().getPlayer().getPosition().getX() + ", y: " + BeamsClient.getInstance().getScene().getPlayer().getPosition().getY() + ", z: " + BeamsClient.getInstance().getScene().getPlayer().getPosition().getZ());
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_1) && !Keyboard.isRepeatEvent()){
            BeamsClient.getInstance().getScene().getPlayer().fireBullet();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_2)){
            BeamsClient.getInstance().getScene().getPlayer().fireHalo();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
            DisplayManager.FPS_CAP++;
            System.out.println(DisplayManager.FPS_CAP);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            DisplayManager.FPS_CAP--;
            System.out.println(DisplayManager.FPS_CAP);
        }
    }

}
