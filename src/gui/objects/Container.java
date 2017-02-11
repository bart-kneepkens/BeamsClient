/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.objects;

import gui.lib.GUIElement;
import gui.lib.GUIParent;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import gui.lib.GUIRenderable;
import gui.lib.MouseActor;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Blackened
 */
public class Container implements GUIRenderable, GUIParent {
    
    private final List<GUIRenderable> children;

    private final Panel panel;
    
    private int paddingLeft = 0;
    private int paddingTop = 0;

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
        child.getGUIElement().increasePosition(this.panel.getGUIElement().getPosition().getX() + this.paddingLeft, this.panel.getGUIElement().getPosition().getY() + this.paddingTop);
        child.getGUIElement().setZ_index(this.getGUIElement().getZ_index() + child.getGUIElement().getZ_index());
        this.children.add(child);
    }

    @Override
    public void load() {
        this.children.forEach(x
                -> {
            x.load();
        });
    }

    public void loadBackground(String name) throws IOException {
        this.panel.loadTexture(name);
    }

    public void enableBackground() {
        this.panel.load();
        this.parent.addChild(this.panel);
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
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
                if(x instanceof MouseActor){
                    ((MouseActor) x).unsubscribeToUserInput();
                }
        });
        this.children.clear();
        
        this.getParent().removeChild(this.panel);
        
        GUIRenderable.super.unload();
    }
    
    @Override
    public void hide(){
        this.children.forEach(x -> {
                this.getParent().removeChild(x);
                if(x instanceof MouseActor){
                    ((MouseActor) x).unsubscribeToUserInput();
                }
        });
        
        this.getParent().removeChild(this.panel);
    }
    
    @Override
    public void show(){
        this.children.forEach(x -> {
            this.getParent().addChild(x);
            });
        this.getParent().addChild(this.panel);
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
