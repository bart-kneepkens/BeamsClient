/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.lib;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import rx.subjects.PublishSubject;
import userInput.MouseState;

/**
 *
 * @author Blackened
 */
public class MouseInput{

    private static PublishSubject<MouseState> mouseSubject = PublishSubject.create();

    private static boolean btn1Pressed;

    private static Vector2f pressOrigin;

    private static boolean btn2Pressed;
    
    private static MouseState mouseState;

    public static void checkInputs() {
        mouseState = new MouseState(false, false, Mouse.getX(), Mouse.getY(), Mouse.getDX(), Mouse.getDY(), Mouse.getDWheel());
        if (!Mouse.isGrabbed()) {
            if (btn2Pressed || btn1Pressed) {
                Mouse.setGrabbed(true);
            }
        }
        else{
            if(!btn2Pressed && !btn1Pressed){
                Mouse.setGrabbed(false);
            }
        }
        // Checks for main 2 mouse buttons
        if (Mouse.isButtonDown(0) && !btn1Pressed) {
            btn1Pressed = true;
            pressOrigin = new Vector2f(mouseState.getX(), mouseState.getY());
            mouseState = mouseState.button0Down(true);
            mouseState = mouseState.pressOrigin(pressOrigin);
        } else if (Mouse.isButtonDown(0) && btn1Pressed) {
            mouseState = mouseState.button0Down(true);
            mouseState = mouseState.pressOrigin(pressOrigin);
        } else if (!Mouse.isButtonDown(0) && btn1Pressed) {
            btn1Pressed = false;
            Mouse.setCursorPosition((int) pressOrigin.x, (int) ((int) Display.getHeight() -pressOrigin.y));
        }
        if (Mouse.isButtonDown(1) && !btn2Pressed) {
            btn2Pressed = true;
            pressOrigin = new Vector2f(mouseState.getX(), mouseState.getY());
            mouseState = mouseState.button1Down(true);
            mouseState = mouseState.pressOrigin(pressOrigin);
        } else if (Mouse.isButtonDown(1) && btn2Pressed) {
            mouseState = mouseState.button1Down(true);
            mouseState = mouseState.pressOrigin(pressOrigin);
        } else if (!Mouse.isButtonDown(1) && btn2Pressed) {
            btn2Pressed = false;
            Mouse.setCursorPosition((int) pressOrigin.x, (int) ((int) Display.getHeight()-pressOrigin.y));
        }
        mouseSubject.onNext(mouseState);
    }

    public static PublishSubject<MouseState> getMouseSubject() {
        return mouseSubject;
    }
    
    public static void clearSubject(){
        mouseSubject = null;
    }
}
