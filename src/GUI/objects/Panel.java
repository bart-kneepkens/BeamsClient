/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIElement;
import GUI.lib.Renderable;
import GUI.lwjgl.GUIElementLoader;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Blackened
 */
public class Panel implements Renderable {

    private final GUIElement guiElement;

    public Panel(int width, int height, Vector2f position, int z_index) throws IOException {
        this.guiElement = new GUIElement(width, height, position, z_index);
    }

//    public Panel(Texture texture, Vector2f position, int z_index) {
//        this.guiElement = new GUIElement(100, 100, position, z_index);
//        this.guiElement.setTextureID(texture.getTextureID());
//    }
//    
    

    public void loadTexture(String name) throws IOException {
        this.guiElement.setTextureID(GUIElementLoader.loadTexture(name));
    }

    @Override
    public GUIElement getGUIElement() {
        return this.guiElement;
    }

}
