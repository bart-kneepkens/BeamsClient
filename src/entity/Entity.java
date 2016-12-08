/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.texture.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Entity {

    /**
     * The textured model that is being used by this entity.
     */
    private TexturedModel model;

    /**
     * The position of the entity.
     */
    private Vector3f position;

    /**
     * The Euler rotation.
     */
    private Vector3f rotation;

    /**
     * The scale of the entity.
     */
    private float scale;

    /**
     * Creates a new instance of the Entity class.
     *
     * @param model The textured model that is being used by this entity.
     * @param position The position of the entity.
     * @param rotation The Euler rotation of the entity.
     * @param scale The scale of the entity.
     */
    public Entity(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }


    /**
     * Increases the position of the entity.
     *
     * @param dx
     * @param dy
     * @param dz
     * <b>Note:</b> this vector can not be normalized!
     */
    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public Vector3f calculateTranslation(float dx, float dy, float dz) {
        return new Vector3f(this.position.getX() + dx, this.position.getY() + dy, this.position.getZ() + dz);
    }

    /**
     * Increases the rotation of the entity.
     *
     * @param vector The vector that translates the rotation.
     * <b>Note:</b> this vector can not be normalized!
     */
    public void increaseRotation(Vector3f vector) {
        this.rotation.x += vector.x;
        this.rotation.y += vector.y;
        this.rotation.z += vector.z;
    }

    
    

    /**
     * Getter for the textured model of this entity.
     *
     * @return The textured model.
     */
    public TexturedModel getModel() {
        return model;
    }

    /**
     * Setter for the textured model of this entity.
     *
     * @param model The textured model.
     */
    public void setModel(TexturedModel model) {
        this.model = model;
    }

    /**
     * Getter for the position of this entity.
     *
     * @return The position.
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Setter for the position of this entity.
     *
     * @param position The position.
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Getter for the rotation of this entity.
     *
     * @return The rotation.
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * Setter for the rotation of this entity.
     *
     * @param rotation The rotation.
     */
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    /**
     * Getter for the scale of this entity.
     *
     * @return The scale.
     */
    public float getScale() {
        return scale;
    }

    /**
     * Setter for the scale of this entity.
     *
     * @param scale The scale.
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

}
