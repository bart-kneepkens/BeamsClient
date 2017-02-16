/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.lib;

/**
 *
 * @author Blackened
 */
public class Event {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private MouseState mouseState;

    private Object sender;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public MouseState getMouseState() {
        return mouseState;
    }

    public Object getSender() {
        return sender;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Event(MouseState mouseState, Object sender) {
        this.mouseState = mouseState;
        this.sender = sender;
    }
//</editor-fold>

}
