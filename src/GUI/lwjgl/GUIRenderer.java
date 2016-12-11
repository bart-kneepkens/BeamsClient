/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.lwjgl;

import GUI.lib.GUIElement;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import static toolbox.AttributeListPosition.TEXTURE_COORDS;
import static toolbox.AttributeListPosition.VERTEX_POSITIONS;
import renderEngine.Renderer;
import toolbox.Maths;

/**
 * Renders GUI elements. IE elements that don't transform due to view matrix
 * and/or projection matrix.
 *
 * @author Blackened
 */
public class GUIRenderer extends GUIShader implements Renderer<GUIElement> {

    public GUIRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    /**
     * Renders an instance of RawModel to the screen.
     *
     * @param element
     */
    @Override
    public void render(GUIElement element) {

        GL30.glBindVertexArray(element.getVaoID());
        GL20.glEnableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glEnableVertexAttribArray(TEXTURE_COORDS);

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector2f(((2.0f * element.getPosition().x) / Display.getWidth()) - 1,
                        ((2.0f * element.getPosition().y) / Display.getHeight()) - 1),
                element.getRotation(),
                element.getWidth() / (float) (Display.getWidth() / 2),
                element.getHeight() / (float) (Display.getHeight() / 2));

        this.loadUniformMatrix("transformationMatrix", transformationMatrix);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, element.getTextureID());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);

        GL20.glDisableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glDisableVertexAttribArray(TEXTURE_COORDS);
        GL30.glBindVertexArray(0);
    }
}
