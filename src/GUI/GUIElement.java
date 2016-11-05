/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import org.lwjgl.util.vector.Vector2f;

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
    private Vector2f rotation;

    private int vaoID = -1;

    private final float[] origin = {
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f,
        -0.5f, 0.5f, 0f
    };

    private final float[] textureCoords = {
        0, 0,
        0, 1,
        1, 1,
        1, 1,
        1, 0,
        0, 0
    };

    public GUIElement(int width, int height, Vector2f position, Vector2f rotation) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.rotation = rotation;
    }

    public void load() {
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

    public Vector2f getRotation() {
        return rotation;
    }

    public float[] getOrigin() {
        return origin;
    }

}
