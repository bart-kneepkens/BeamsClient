/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import GUI.GUIElement;
import userInput.MouseState;

/**
 *
 * @author Blackened
 */
public class Event {
    
    private MouseState mouseState;
    
    private GUIElement sender;

    public Event(MouseState mouseState, GUIElement sender) {
        this.mouseState = mouseState;
        this.sender = sender;
    }

    public MouseState getMouseState() {
        return mouseState;
    }

    public GUIElement getSender() {
        return sender;
    }
    
}
