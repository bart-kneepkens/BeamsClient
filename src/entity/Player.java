/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beamsClient.BeamsClient;
import entity.texture.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 *
 * @author Blackened
 */
public class Player extends Entity {

    private static final float TERRAIN_HEIGHT = 0;

    private static final float GRAVITY = -50f;
    private static final float JUMP_POWER = 10;

    private float movementSpeed = 10f;
    private float turnSpeed = 4f;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public void moveForward() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY())) * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY())) * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(new Vector3f(dx, 0, dz));
    }

    public void moveBackward() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY())) * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY())) * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(new Vector3f(-dx, 0, -dz));
    }

    public void strafeLeft() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY() - 0.5 * Math.PI)) * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY() - 0.5 * Math.PI)) * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(new Vector3f(-dx, 0, -dz));
    }

    public void strafeRight() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY() + 0.5 * Math.PI)) * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY() + 0.5 * Math.PI)) * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(new Vector3f(-dx, 0, -dz));
    }

    public void turnRight() {
        super.increaseRotation(new Vector3f(0, -1 * turnSpeed * DisplayManager.getFrameTimeSeconds(), 0));
    }

    public void turnLeft() {
        super.increaseRotation(new Vector3f(0, 1 * turnSpeed * DisplayManager.getFrameTimeSeconds(), 0));
    }

    public void gravitate() {
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(new Vector3f(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0));
        float terrainHeight = BeamsClient.scene.getTerrain().getHeightOfTerrain(super.getPosition().getX(), super.getPosition().getZ());
        if (super.getPosition().y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = terrainHeight;
        }
        
    }

    public void jump() {
        if (!this.isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            this.isInAir = true;
        }
    }
}
