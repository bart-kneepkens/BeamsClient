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
public class Player extends Entity {

    private float movementSpeed = 0.1f;
    private float turnSpeed = 0.02f;

    public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public void moveForward() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY()));
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY()));
        super.increasePosition(new Vector3f(dx, 0, dz));
    }

    public void moveBackward() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY()));
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY()));
        super.increasePosition(new Vector3f(-dx, 0, -dz));
    }

    public void turnRight() {
        super.increaseRotation(new Vector3f(0, -1 * turnSpeed, 0));
    }

    public void turnLeft() {
        super.increaseRotation(new Vector3f(0, 1 * turnSpeed, 0));
    }
}
