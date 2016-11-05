/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;
import org.lwjgl.input.Mouse;
import rx.subjects.PublishSubject;

/**
 *
 * @author Blackened
 */
public class MouseInput{
    
    private static final PublishSubject<MouseState> mouseSubject = PublishSubject.create();
    
    
    
    public static void checkInputs(){
        MouseState mouseState = new MouseState(false, false, Mouse.getX(), Mouse.getY());
        if(Mouse.isButtonDown(0)){
            mouseState = mouseState.button0Down(true);
        }
        if(Mouse.isButtonDown(1)){
            mouseState = mouseState.button1Down(true);
        }
            mouseSubject.onNext(mouseState);
    }   
    
    

    public static PublishSubject<MouseState> getMouseSubject() {
        return mouseSubject;
    }
}
