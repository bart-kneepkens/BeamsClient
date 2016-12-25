/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import toolbox.Autonomous;
import GUI.lib.GUIRenderable;

/**
 *
 * @author Blackened
 */
public class GUI {
    
    private final List<GUIRenderable> elements;
    
    private final List<Autonomous> autonomousElements;

    public GUI() {
        this.elements = new ArrayList<>();
        this.autonomousElements = new ArrayList<>();
    }
    
    public void addElement(GUIRenderable element){
        this.elements.add(element);
        Collections.sort(elements, (GUIRenderable o1, GUIRenderable o2) -> {
            Integer z_index1 = o1.getGUIElement().getZ_index();
            Integer z_index2 = o2.getGUIElement().getZ_index();
            return z_index1.compareTo(z_index2);
        });
        
        if(element instanceof Autonomous){
            autonomousElements.add((Autonomous) element);
        }
    }

    public List<GUIRenderable> getElements() {
        return elements;
    }

    public List<Autonomous> getAutonomousElements() {
        return autonomousElements;
    }
    
    public void removeElement(GUIRenderable element){
        this.elements.remove(element);
    }
    
    
    
    
    
    
}
