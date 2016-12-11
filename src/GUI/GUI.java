/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.lib.GUIElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Blackened
 */
public class GUI {
    
    private List<GUIElement> guiElements;

    public GUI() {
        this.guiElements = new ArrayList<>();
    }
    
    public void addElement(GUIElement element){
        this.guiElements.add(element);
        Collections.sort(guiElements, new Comparator<GUIElement>() {
            @Override
            public int compare(GUIElement o1, GUIElement o2) {
                Integer z_index1 = o1.getZ_index();
                Integer z_index2 = o2.getZ_index();
                return z_index1.compareTo(z_index2);
            }
        });
    }

    public List<GUIElement> getGuiElements() {
        return guiElements;
    }
    
    
    
    
}
