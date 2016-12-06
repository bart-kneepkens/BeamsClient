/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.lib;

import GUI.lwjgl.GUIElementLoader;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
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

    public int activeTextureID;

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

    private float[] textureCoords = {
        0, 0,
        0, 1,
        1, 1,
        1, 1,
        1, 0,
        0, 0
    };

    /**
     *
     * @param width
     * @param height
     * @param position The position of the left bottom corner of the GUI-element
     * @param rotation
     */
    public GUIElement(int width, int height, Vector2f position, Vector3f rotation) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.rotation = rotation;
    }

    public GUIElement(int width, int height, Vector2f position) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.rotation = new Vector3f(0, 0, 0);
    }

    protected void load() {
        this.vaoID = GUIElementLoader.loadToVAO(this);
    }

    public void increasePosition(Vector2f delta) {
        this.position = new Vector2f(this.position.x + delta.x, this.position.y + delta.y);
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getActiveTextureID() {
        return activeTextureID;
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

}