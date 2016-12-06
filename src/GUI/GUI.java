/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.lib.GUIElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Blackened
 */
public class GUI {
    
    private Map<GUIElement, Integer> guiElements;

    public GUI() {
        this.guiElements = new HashMap<>();
    }
    
    public void addElement(GUIElement element, int z_index){
        this.guiElements.put(element, z_index);
    }

    public Map<GUIElement, Integer> getGuiElements() {
        return Collections.unmodifiableMap(guiElements);
    }
    
    
    
    
}
