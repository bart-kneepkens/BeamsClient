/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.lib;

import userInput.MouseState;

/**
 *
 * @author Blackened
 */
public class Event {
    
    private MouseState mouseState;
    
    private Object sender;

    public Event(MouseState mouseState, Object sender) {
        this.mouseState = mouseState;
        this.sender = sender;
    }

    public MouseState getMouseState() {
        return mouseState;
    }

    public Object getSender() {
        return sender;
    }
    
}
