/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.lib.Renderable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import toolbox.Autonomous;

/**
 *
 * @author Blackened
 */
public class GUI {
    
    private final List<Renderable> elements;
    
    private final List<Autonomous> autonomousElements;

    public GUI() {
        this.elements = new ArrayList<>();
        this.autonomousElements = new ArrayList<>();
    }
    
    public void addElement(Renderable element){
        this.elements.add(element);
        Collections.sort(elements, (Renderable o1, Renderable o2) -> {
            Integer z_index1 = o1.getGUIElement().getZ_index();
            Integer z_index2 = o2.getGUIElement().getZ_index();
            return z_index1.compareTo(z_index2);
        });
        
        if(element instanceof Autonomous){
            autonomousElements.add((Autonomous) element);
        }
    }

    public List<Renderable> getElements() {
        return elements;
    }

    public List<Autonomous> getAutonomousElements() {
        return autonomousElements;
    }
    
    public void removeElement(Renderable element){
        this.elements.remove(element);
    }
    
    
    
    
    
    
}
