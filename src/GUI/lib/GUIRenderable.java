/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.lib;



/**
 *
 * @author Blackened
 */
public interface GUIRenderable {
    
    GUIElement getGUIElement();
    
    GUIParent getParent();
    
    default void load(){
        this.getGUIElement().load();
    }
    
    default void unload(){
        this.getGUIElement().unload();
    }
    
    default void show(){
        this.getParent().addChild(this);
    }
    
    default void hide(){
        this.getParent().removeChild(this);
    }
}
