/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import beamsClient.BeamsClient;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import renderEngine.DisplayManager;

/**
 *
 * @author Blackened
 */
public class KeyboardInput {

    public static void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            BeamsClient.exit();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            BeamsClient.getScene().getPlayer().moveForward();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            BeamsClient.getScene().getPlayer().moveBackward();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (Mouse.isButtonDown(1)) {
                BeamsClient.getScene().getPlayer().strafeRight();
            } else {
                BeamsClient.getScene().getPlayer().turnRight();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (Mouse.isButtonDown(1)) {
                BeamsClient.getScene().getPlayer().strafeLeft();
            } else {
                BeamsClient.getScene().getPlayer().turnLeft();
            }
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
            BeamsClient.getScene().getPlayer().strafeLeft();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            BeamsClient.getScene().getPlayer().strafeRight();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            BeamsClient.getScene().getPlayer().jump();
        }
        if(Mouse.isButtonDown(0) && Mouse.isButtonDown(1)){
            BeamsClient.getScene().getPlayer().moveForward();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F)){
            System.out.println("x: " + BeamsClient.getScene().getPlayer().getPosition().getX() + ", y: " + BeamsClient.getScene().getPlayer().getPosition().getY() + ", z: " + BeamsClient.getScene().getPlayer().getPosition().getZ());
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_1)){
            BeamsClient.getScene().getPlayer().fireBullet();
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
