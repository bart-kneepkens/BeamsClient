/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.lib;

import GUI.lwjgl.GUIElementLoader;
import GUI.lwjgl.GUIRenderer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import static toolbox.AttributeListPosition.VERTEX_POSITIONS;
import toolbox.Maths;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import static toolbox.AttributeListPosition.TEXTURE_COORDS;
import userInput.MouseState;

/**
 *
 * @author Blackened
 */
public class GUIElement {

    /**
     * IN PIXELS!
     */
    private int width;
    private int height;
    
    private int z_index;

    private int textureID;
    /**
     * IN PIXELS!
     */
    private Vector2f position;
    private Vector3f rotation;

    private int vaoID = -1;

    private final float[] origin = {
        0f, 1f, 0f,
        0f, 0f, 0f,
        1f, 0f, 0f,
        1f, 0f, 0f,
        1f, 1f, 0f,
        0f, 1f, 0f
    };

    public float[] textureCoords = new float[]{
        0, 0,
        0, 1f,
        1f, 1f,
        1f, 1f,
        1f, 0,
        0, 0
    };

    /**
     *
     * @param width
     * @param height
     * @param position The position of the left bottom corner of the GUI-element
     * @param rotation
     */
    public GUIElement(int width, int height, Vector2f position, int z_index, Vector3f rotation) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.z_index = z_index;
        this.rotation = rotation;
    }

    public GUIElement(int width, int height, Vector2f position, int z_index) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.z_index = z_index;
        this.rotation = new Vector3f(0, 0, 0);
    }

    public void loadTextureCoords(float[] newTextureCoords) {
        this.textureCoords = newTextureCoords;
        GUIElementLoader.reloadTextureCoords(this);
    }

    public void load() {
        this.vaoID = GUIElementLoader.loadToVAO(this);
    }

    public void increasePosition(float dx, float dy) {
        this.position = new Vector2f(this.position.x + dx, this.position.y + dy);
    }
    
    public int getZ_index() {
        return z_index;
    }

    
    public int getVaoID() {
        return vaoID;
    }

    public int getTextureID() {
        return textureID;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float[] getOrigin() {
        return origin;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
    
    

    public void setTextureCoords(float[] textureCoords) {
        this.textureCoords = textureCoords;
    }

    public boolean inRange(MouseState mouseState) {
        if (mouseState.getX() > this.getPosition().x
                && mouseState.getX() < this.getPosition().x + this.getWidth()) {
            return mouseState.getY() > this.getPosition().y
                    && mouseState.getY() < this.getPosition().y + this.getHeight();
        }
        return false;
    }

    public boolean inRange(Vector2f pos) {
        if (pos.x > this.getPosition().x
                && pos.x < this.getPosition().x + this.getWidth()) {
            return pos.y > this.getPosition().y
                    && pos.y < this.getPosition().y + this.getHeight();
        }
        return false;
    }

    public void render(GUIRenderer renderer) {
        GL30.glBindVertexArray(this.getVaoID());
        GL20.glEnableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glEnableVertexAttribArray(TEXTURE_COORDS);

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector2f(((2.0f * this.getPosition().x) / Display.getWidth()) - 1,
                        ((2.0f * this.getPosition().y) / Display.getHeight()) - 1),
                this.getRotation(),
                this.getWidth() / (float) (Display.getWidth() / 2),
                this.getHeight() / (float) (Display.getHeight() / 2));

        renderer.loadUniformMatrix("transformationMatrix", transformationMatrix);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.getTextureID());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);

        GL20.glDisableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glDisableVertexAttribArray(TEXTURE_COORDS);
        GL30.glBindVertexArray(0);
    }

}
