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
public interface Renderable {
    
    GUIElement getGUIElement();
    
    default void load(){
        this.getGUIElement().load();
    }
    
}