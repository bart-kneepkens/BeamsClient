/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

import GUI.GUIElement;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import static renderEngine.AttributeListName.TEXTURE_COORDS;
import static renderEngine.AttributeListName.VERTEX_POSITIONS;
import shaders.GUI.GUIShader;
import toolbox.Maths;

/**
 * Renders GUI elements. IE elements that don't transform due to view matrix
 * and/or projection matrix.
 *
 * @author Blackened
 */
public class GUIRenderer {

    /**
     * Clears the display and sets its background colour.
     */
    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0, 0, 0, 1);
    }

    /**
     * Renders an instance of RawModel to the screen.
     *
     * @param element
     * @param shader The shader that is being used while rendering.
     */
    public void render(GUIElement element, GUIShader shader) {

        GL30.glBindVertexArray(element.getVaoID());
        enableAttributeArray(VERTEX_POSITIONS);
        enableAttributeArray(TEXTURE_COORDS);

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector2f(((2.0f * element.getPosition().x) / Display.getWidth()) - 1,
                        ((2.0f * element.getPosition().y) / Display.getHeight()) - 1),
                element.getRotation(),
                element.getWidth() / (float) (Display.getWidth() / 2),
                element.getHeight() / (float) (Display.getHeight() / 2));
        shader.loadUniformMatrix("transformationMatrix", transformationMatrix);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, element.getActiveTextureID());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);

        disableAttributeArray(VERTEX_POSITIONS);
        disableAttributeArray(TEXTURE_COORDS);
        GL30.glBindVertexArray(0);
    }

    /**
     * Enables the attribute list specified by the name.
     *
     * @param model The model for which to enable the attribute list.
     * @param attribName The name of the attributeList.
     */
    private void enableAttributeArray(AttributeListName attribName) {
        GL20.glEnableVertexAttribArray(attribName.getNumVal());
    }

    /**
     * Disables an enabled attribute list
     */
    private void disableAttributeArray(AttributeListName attribName) {
        GL20.glDisableVertexAttribArray(attribName.getNumVal());
    }

}
