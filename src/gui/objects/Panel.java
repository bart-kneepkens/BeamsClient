/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.objects;

import gui.lib.GUIElement;
import gui.lib.GUIParent;
import gui.lwjgl.GUIElementLoader;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import gui.lib.GUIRenderable;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Blackened
 */
public class Panel implements GUIRenderable {

//<editor-fold defaultstate="collapsed" desc="Properties">
    private GUIParent parent;

    private final GUIElement guiElement;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    @Override
    public GUIElement getGUIElement() {
        return this.guiElement;
    }

    @Override
    public GUIParent getParent() {
        return this.parent;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Constructors">
    public Panel(GUIParent parent, int width, int height, Vector2f position, int z_index) throws IOException {
        this.guiElement = new GUIElement(width, height, position, z_index);
        this.parent = parent;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void loadTexture(String name) throws IOException {
        Texture texture = GUIElementLoader.loadTexture(name);
        this.guiElement.setTexture(texture);

        float[] newTextureCoords = new float[]{
            0, 0,
            0, texture.getHeight(),
            texture.getWidth(), texture.getHeight(),
            texture.getWidth(), texture.getHeight(),
            texture.getWidth(), 0,
            0, 0
        };

        this.guiElement.setTextureCoords(newTextureCoords);
    }
//</editor-fold>

}
