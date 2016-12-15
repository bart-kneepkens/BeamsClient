/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.lwjgl;

import GUI.lib.Renderable;
import org.lwjgl.opengl.GL11;
import renderEngine.Renderer;

/**
 * Renders GUI elements. IE elements that don't transform due to view matrix
 * and/or projection matrix.
 *
 * @author Blackened
 */
public class GUIRenderer extends GUIShader implements Renderer<Renderable> {

    public GUIRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }
    
    @Override
    public void render(Renderable element){
        element.render(this);
    }
}
