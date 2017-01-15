/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.lib;

import GUI.lwjgl.GUIElementLoader;
import GUI.objects.Label;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
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

    private Texture texture;
    /**
     * IN PIXELS!
     */
    private Vector2f position;
    private Vector3f rotation;

    private int vaoID = -1;
    
    private Label label;

    private final float[] origin = {
        0f, 0f, 0f,
        0f, -1f, 0f,
        1f, -1f, 0f,
        1f, -1f, 0f,
        1f, 0f, 0f,
        0f, 0f, 0f
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
     * @param z_index
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
        this.vaoID = GUIElementLoader.loadVAO(this);
    }
    
    public void unload(){
        GUIElementLoader.unloadVAO(this);
    }

    public void increasePosition(float dx, float dy) {
        this.position = new Vector2f(this.position.x + dx, this.position.y + dy);
        if(this.label != null)this.label.setPosition(new Vector2f(this.label.getPosition().getX() + dx, this.label.getPosition().getY() + dy));
    }
    
    public int getZ_index() {
        return z_index;
    }

    
    public int getVaoID() {
        return vaoID;
    }

    public Label getLabel() {
        return label;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public void setZ_index(int z_index) {
        this.z_index = z_index;
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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
    
    
    
    

    public void setTextureCoords(float[] textureCoords) {
        this.textureCoords = textureCoords;
    }

    public boolean inRange(MouseState mouseState) {
        if (mouseState.getX() > this.getPosition().x
                && mouseState.getX() < this.getPosition().x + this.getWidth()) {
            return Display.getHeight() -  mouseState.getY() > this.getPosition().y
                    && Display.getHeight() - mouseState.getY() < this.getPosition().y + this.getHeight();
        }
        return false;
    }

    public boolean inRange(Vector2f pos) {
        if (pos.x > this.getPosition().x
                && pos.x < this.getPosition().x + this.getWidth()) {
            return Display.getHeight() - pos.y > this.getPosition().y
                    && Display.getHeight() - pos.y < this.getPosition().y + this.getHeight();
        }
        return false;
    }

}
