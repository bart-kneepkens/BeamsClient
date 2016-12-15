/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIElement;
import GUI.lib.Renderable;
import GUI.lwjgl.GUIRenderer;

/**
 *
 * @author Blackened
 */
public class Container implements Renderable{
    
    private GUIElement guiElement;

    @Override
    public GUIElement getGUIElement() {
        return this.guiElement;
    }
    
    @Override
    public void render(GUIRenderer renderer){
        this.guiElement.render(renderer);
        //TODO: RENDER CHILDREN
        
    }
    
    
    
}
