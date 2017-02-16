/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import game.camera.Camera;
import game.entity.Player;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import renderEngine.DisplayManager;

/**
 *
 * @author Blackened
 */
public class GameInputHandler {

    //<editor-fold defaultstate="collapsed" desc="Properties">
// Everything this GameInputHandler should reach:
    private Camera camera;
    private Player player;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public GameInputHandler(Camera camera, Player player) {
        this.camera = camera;
        this.player = player;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void handleInput() {
        handleMouseInput();
        handleKeyInput();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    private void handleMouseInput() {
        int MouseDX = Mouse.getDX();
        int MouseDY = Mouse.getDY();
        int MouseDW = Mouse.getDWheel();

        if (Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
            this.camera.changePitch(MouseDY * DisplayManager.getFrameTimeSeconds() * 10);
            this.camera.changeYaw(MouseDX * DisplayManager.getFrameTimeSeconds() * -0.5f);
        }
        if (MouseDW != 0) {
            this.camera.changeZoomLevel(MouseDW * DisplayManager.getFrameTimeSeconds() * 0.1f);
        }
        if (Mouse.isButtonDown(1)) {
            this.player.turnLeft(MouseDX * DisplayManager.getFrameTimeSeconds() * -0.1f);
            this.camera.changePitch(MouseDY * DisplayManager.getFrameTimeSeconds() * 10);
        }
        if (Mouse.isButtonDown(0) && Mouse.isButtonDown(1)) {
            this.player.moveForward(DisplayManager.getFrameTimeSeconds());
        }
    }

    private void handleKeyInput() {
        // Single Keys
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_1) {
                    this.player.castSpell1();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_2) {
                    this.player.castSpell2();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    this.player.jump();
                }
            }
        }

        // Repeat Keys
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.player.moveForward(DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.player.moveBackwards(DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            this.player.strafeLeft(DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            this.player.strafeRight(DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (Mouse.isButtonDown(1)) {
                this.player.strafeLeft(DisplayManager.getFrameTimeSeconds());
            } else {
                this.player.turnLeft(DisplayManager.getFrameTimeSeconds());
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (Mouse.isButtonDown(1)) {
                this.player.strafeRight(DisplayManager.getFrameTimeSeconds());
            } else {
                this.player.turnRight(DisplayManager.getFrameTimeSeconds());
            }
        }

    }
//</editor-fold>

}
