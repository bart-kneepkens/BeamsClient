/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.UserInterface;
import GUI.lib.GUIElement;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import GUI.lib.GUIRenderable;

/**
 *
 * @author Blackened
 */
public class Container implements GUIRenderable {

    private final GUIElement guiElement;

    private final List<GUIRenderable> children;

    private boolean rendered = false;
    
    protected UserInterface userInterface;

    public Container(int width, int height, Vector2f position, int z_index, UserInterface userInterface) {
        this.userInterface = userInterface;
        this.guiElement = new GUIElement(width, height, position, z_index);
        this.children = new ArrayList<>();
    }

    @Override
    public final GUIElement getGUIElement() {
        return this.guiElement;
    }

    public final void addChild(GUIRenderable child, boolean render) {
        child.getGUIElement().increasePosition(this.guiElement.getPosition().getX(), this.guiElement.getPosition().getY());
        this.children.add(child);
        if(render){
            this.userInterface.getGui().addElement(child);
        }
    }
    
    protected void close(){
        this.userInterface.getGui().removeElement(this);
        this.children.forEach(x -> this.userInterface.getGui().removeElement(x));
    }

    @Override
    public void load() {
        this.guiElement.load();
        this.children.forEach(x -> x.load());
    }

    public final boolean isRendered() {
        return rendered;
    }

    public final void setRendered(boolean value) {
        this.rendered = value;
    }

}
