/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIElement;
import GUI.lib.GUIParent;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import GUI.lib.GUIRenderable;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Blackened
 */
public class Container implements GUIRenderable, GUIParent {
    
    private final List<GUIRenderable> children;

    private final Panel panel;

    protected GUIParent parent;

    public Container(GUIParent parent, int width, int height, Vector2f position, int z_index) throws IOException {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.panel = new Panel(this, width, height, position, z_index);
    }

    @Override
    public final GUIElement getGUIElement() {
        return this.panel.getGUIElement();
    }

    @Override
    public final void addChild(GUIRenderable child) {
        child.getGUIElement().increasePosition(this.panel.getGUIElement().getPosition().getX(), this.panel.getGUIElement().getPosition().getY());
        child.getGUIElement().setZ_index(this.getGUIElement().getZ_index() + child.getGUIElement().getZ_index());
        this.children.add(child);
    }

    @Override
    public void load() {
        this.children.forEach(x
                -> {
            x.load();
            this.parent.addChild(x);
        });
    }

    public void loadBackground(String name) throws IOException {
        this.panel.loadTexture(name);
    }

    public void enableBackground() {
        this.panel.load();
        this.parent.addChild(this.panel);
    }

    public void disableBackground() {
        this.panel.unload();
        this.parent.removeChild(this.panel);
    }

    @Override
    public GUIParent getParent() {
        return this.parent;
    }

    @Override
    public void unload() {
        this.children.forEach(x -> {
                this.getParent().removeChild(x);
                x.unload();
        });
        this.children.clear();
        
        this.getParent().removeChild(this.panel);
        
        GUIRenderable.super.unload();
    }
    
    public void hide(){
        this.children.forEach(x -> {
                this.getParent().removeChild(x);
        });
        
        this.getParent().removeChild(this.panel);
    }

    @Override
    public List<GUIRenderable> getChildren() {
        return this.children;
    }

    @Override
    public void removeChild(GUIRenderable child) {
        this.children.remove(child);
        this.parent.removeChild(child);
    }

    @Override
    public void addChildren(Collection<GUIRenderable> children) {
        this.children.addAll(children);
        this.parent.addChildren(children);
    }

}
