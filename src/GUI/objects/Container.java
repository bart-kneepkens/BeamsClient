/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIElement;
import GUI.lib.Renderable;
import GUI.lwjgl.GUIRenderer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Blackened
 */
public class Container implements Renderable {

    private final GUIElement guiElement;

    private final List<Renderable> children;

    private boolean rendered = false;

    public Container(int width, int height, Vector2f position, int z_index) {
        this.guiElement = new GUIElement(width, height, position, z_index);
        this.children = new ArrayList<>();
    }

    @Override
    public final GUIElement getGUIElement() {
        return this.guiElement;
    }

    @Override
    public void render(GUIRenderer renderer) {
        this.children.forEach(x -> x.render(renderer));
        if (this.rendered) {
            this.guiElement.render(renderer);
        }
    }

    public final void addChild(Renderable child) {
        child.getGUIElement().increasePosition(this.guiElement.getPosition().getX(), this.guiElement.getPosition().getY());
        this.children.add(child);
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
