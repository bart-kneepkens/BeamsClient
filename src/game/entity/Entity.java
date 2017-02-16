/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity;

import beamsClient.BeamsClient;
import game.entity.models.TexturedModel;
import java.util.List;
import java.util.Map;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Entity {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     * For testing purposes only!
     */
    private final String name;

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
     * The radius of the collision circle for this entity.
     */
    private final float collisionRadius;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getName() {
        return name;
    }

    public float getCollisionRadius() {
        return collisionRadius;
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
    public final Vector3f getPosition() {
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new instance of the Entity class.
     *
     * @param name For testing purposes only!
     * @param model The textured model that is being used by this entity.
     * @param position The position of the entity.
     * @param rotation The Euler rotation of the entity.
     * @param scale The scale of the entity.
     */
    public Entity(String name, TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.name = name;
        this.collisionRadius = 0.25f;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
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
     * @param x
     * @param y
     * @param z
     */
    public void increaseRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Protected Methods">
    protected void checkCollisions() {
        Vector2f ownPosition = new Vector2f(this.getPosition().getX(), this.getPosition().getZ());

        for (Map.Entry entry : BeamsClient.getInstance().getScene().getEntities().entrySet()) {
            for (Entity entity : (List<Entity>) entry.getValue()) {
                if (entity != this) {

                    Vector2f entityPosition = new Vector2f(entity.getPosition().getX(), entity.getPosition().getZ());
                    Vector2f distanceVector = new Vector2f();
                    Vector2f.sub(ownPosition, entityPosition, distanceVector);
                    float distance = distanceVector.length();
                    if (distance < this.getCollisionRadius() + entity.getCollisionRadius()) {
                        System.out.println(this.getName() + " is colliding with: " + entity.getName());
                    }
                }

            }
        }
    }

    protected boolean checkCollisions(Vector3f nextPosition) {
        Vector2f ownPosition = new Vector2f(nextPosition.getX(), nextPosition.getZ());

        for (Map.Entry entry : BeamsClient.getInstance().getScene().getEntities().entrySet()) {
            for (Entity entity : (List<Entity>) entry.getValue()) {
                if (entity != this) {

                    Vector2f entityPosition = new Vector2f(entity.getPosition().getX(), entity.getPosition().getZ());
                    Vector2f distanceVector = new Vector2f();
                    Vector2f.sub(ownPosition, entityPosition, distanceVector);
                    float distance = distanceVector.length();
                    if (distance < this.getCollisionRadius() + entity.getCollisionRadius()) {
                        System.out.println(this.getName() + " is colliding with: " + entity.getName());
                        return true;
                    }
                }

            }
        }
        return false;
    }
//</editor-fold>

}
