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
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Container implements Renderable {

    private final GUIElement guiElement;

    private final List<Renderable> children;

    private boolean renderContainer = false;

    public Container(int width, int height, Vector2f position, int z_index) {
        this.guiElement = new GUIElement(width, height, position, z_index);
        this.children = new ArrayList<>();
    }

    @Override
    public GUIElement getGUIElement() {
        return this.guiElement;
    }

    @Override
    public void render(GUIRenderer renderer) {
        this.children.forEach(x -> x.render(renderer));
        if (this.renderContainer) {
            this.guiElement.render(renderer);
        }
    }

    public void addChild(Renderable child) {
        child.getGUIElement().increasePosition(this.guiElement.getPosition());
        this.children.add(child);
    }

    @Override
    public void load() {
        this.guiElement.load();
        this.children.forEach(x -> x.load());
    }

    public boolean renderContainer() {
        return renderContainer;
    }

    public void renderContainer(boolean renderContainer) {
        this.renderContainer = renderContainer;
    }

}
