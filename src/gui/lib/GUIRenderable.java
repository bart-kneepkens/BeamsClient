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
public interface GUIRenderable {

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public GUIElement getGUIElement();

    public GUIParent getParent();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public default void load() {
        this.getGUIElement().load();
    }

    public default void unload() {
        this.getGUIElement().unload();
    }

    public default void show() {
        this.getParent().addChild(this);
    }

    public default void hide() {
        this.getParent().removeChild(this);
    }
//</editor-fold>

}
