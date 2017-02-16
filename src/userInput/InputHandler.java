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
public class InputHandler {

    public static void handleInput() {
        handleMouseInput();
        handleKeyInput();
    }

    private static void handleMouseInput() {
        int MouseDX = Mouse.getDX();
        int MouseDY = Mouse.getDY();
        int MouseDW = Mouse.getDWheel();

        if (Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
            beamsClient.BeamsClient.getInstance().getScene().getCamera().changePitch(MouseDY * DisplayManager.getFrameTimeSeconds() * 10);
            beamsClient.BeamsClient.getInstance().getScene().getCamera().changeYaw(MouseDX * DisplayManager.getFrameTimeSeconds() * -0.5f);
        }
        if (MouseDW != 0) {
            beamsClient.BeamsClient.getInstance().getScene().getCamera().changeZoomLevel(MouseDW * DisplayManager.getFrameTimeSeconds() * 0.1f);
        }
        if (Mouse.isButtonDown(1)) {
            BeamsClient.getInstance().getScene().getPlayer().turnLeft(MouseDX * DisplayManager.getFrameTimeSeconds() * -0.1f);
            beamsClient.BeamsClient.getInstance().getScene().getCamera().changePitch(MouseDY * DisplayManager.getFrameTimeSeconds() * 10);
        }
        if (Mouse.isButtonDown(0) && Mouse.isButtonDown(1)){
            BeamsClient.getInstance().getScene().getPlayer().moveForward(DisplayManager.getFrameTimeSeconds());
        }
    }

    private static void handleKeyInput() {
        // Single Keys
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                // Application keys
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    BeamsClient.getInstance().exit();
                }

                // Player keys
                if (Keyboard.getEventKey() == Keyboard.KEY_1) {
                    BeamsClient.getInstance().getScene().getPlayer().fireBullet();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_2) {
                    BeamsClient.getInstance().getScene().getPlayer().fireHalo();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    BeamsClient.getInstance().getScene().getPlayer().jump();
                }
            }
        }

        // Repeat Keys
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            BeamsClient.getInstance().getScene().getPlayer().moveForward(DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            BeamsClient.getInstance().getScene().getPlayer().moveBackward(DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            BeamsClient.getInstance().getScene().getPlayer().strafeLeft(DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            BeamsClient.getInstance().getScene().getPlayer().strafeRight(DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (Mouse.isButtonDown(1)) {
                BeamsClient.getInstance().getScene().getPlayer().strafeLeft(DisplayManager.getFrameTimeSeconds());
            } else {
                BeamsClient.getInstance().getScene().getPlayer().turnLeft(DisplayManager.getFrameTimeSeconds());
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (Mouse.isButtonDown(1)) {
                BeamsClient.getInstance().getScene().getPlayer().strafeRight(DisplayManager.getFrameTimeSeconds());
            } else {
                BeamsClient.getInstance().getScene().getPlayer().turnRight(DisplayManager.getFrameTimeSeconds());
            }
        }

    }

}
