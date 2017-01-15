/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIElement;
import GUI.lib.GUIParent;
import GUI.lwjgl.GUIElementLoader;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import GUI.lib.GUIRenderable;

/**
 *
 * @author Blackened
 */
public class Panel implements GUIRenderable {
    
    private GUIParent parent;

    private final GUIElement guiElement;

    public Panel(GUIParent parent, int width, int height, Vector2f position, int z_index) throws IOException {
        this.guiElement = new GUIElement(width, height, position, z_index);
        this.parent = parent;
    }
    

    public void loadTexture(String name) throws IOException {
        this.guiElement.setTextureID(GUIElementLoader.loadTexture(name));
    }

    @Override
    public GUIElement getGUIElement() {
        return this.guiElement;
    }

    @Override
    public GUIParent getParent() {
        return this.parent;
    }

}
