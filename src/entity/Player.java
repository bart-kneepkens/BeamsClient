/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beamsClient.BeamsClient;
import entity.texture.TexturedModel;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrain.Terrain;

/**
 *
 * @author Blackened
 */
public class Player extends Entity {

    private static final float GRAVITY = -50f;
    private static final float JUMP_POWER = 10;

    private float movementSpeed = 10f;
    private float turnSpeed = 4f;
    private float upwardsSpeed = 0;

    private List<Bullet> bullets;

    private TexturedModel bulletModel;

    private boolean isInAir = true;

    public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale, TexturedModel bulletModel) {
        super(model, position, rotation, scale);
        this.bulletModel = bulletModel;
        this.bullets = new ArrayList<>();
    }

    public void moveForward() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY())) * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY())) * DisplayManager.getFrameTimeSeconds();
        Vector3f nextPosition = super.calculateTranslation(dx, 0, dz);
        if (isMovementAllowed(nextPosition)) {
            super.increasePosition(dx, 0, dz);
        }

    }

    public void moveBackward() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY())) * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY())) * DisplayManager.getFrameTimeSeconds();
        Vector3f nextPosition = super.calculateTranslation(-dx, 0, -dz);
        if (isMovementAllowed(nextPosition)) {
            super.increasePosition(-dx, 0, -dz);
        }
    }

    public void strafeLeft() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY() - 0.5 * Math.PI)) * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY() - 0.5 * Math.PI)) * DisplayManager.getFrameTimeSeconds();
        Vector3f nextPosition = super.calculateTranslation(-dx, 0, -dz);
        if (isMovementAllowed(nextPosition)) {
            super.increasePosition(-dx, 0, -dz);
        }
    }

    public void strafeRight() {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY() + 0.5 * Math.PI)) * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY() + 0.5 * Math.PI)) * DisplayManager.getFrameTimeSeconds();
        Vector3f nextPosition = super.calculateTranslation(-dx, 0, -dz);
        if (isMovementAllowed(nextPosition)) {
            super.increasePosition(-dx, 0, -dz);
        }
    }

    public void fireBullet() {

        if (Math.abs(Bullet.LAST_ONE_FIRED - DisplayManager.getCurrentTime()) > 200) {
            Bullet bullet = new Bullet(
                    DisplayManager.getCurrentTime(),
                    1000,
                    new Vector3f((float) Math.sin(this.getRotation().getY()) * 2,
                            0,
                            (float) Math.cos(this.getRotation().getY()) * 2),
                    bulletModel,
                    new Vector3f(this.getPosition().getX(), this.getPosition().getY()+1, this.getPosition().getZ()),
                    new Vector3f(0, 0, 0),
                    0.2f);
            BeamsClient.getScene().getEntities().get(this.bulletModel).add(bullet);
            BeamsClient.getScene().getLights().add(bullet.getLight());

            this.bullets.add(bullet);
            Bullet.LAST_ONE_FIRED = DisplayManager.getCurrentTime();
        }
    }

    private boolean isMovementAllowed(Vector3f nextPosition) {
//        return (nextPosition.getX() > Terrain.BORDER_SIZE && nextPosition.getX() < Terrain.SIZE - Terrain.BORDER_SIZE
//                && nextPosition.getZ() > Terrain.BORDER_SIZE && nextPosition.getZ() < Terrain.SIZE - Terrain.BORDER_SIZE);
return true;
    }

    public void turnRight() {
        super.increaseRotation(new Vector3f(0, -1 * turnSpeed * DisplayManager.getFrameTimeSeconds(), 0));
    }

    public void turnLeft() {
        super.increaseRotation(new Vector3f(0, 1 * turnSpeed * DisplayManager.getFrameTimeSeconds(), 0));
    }

    public void update() {
        this.gravitate();
        this.bullets.stream().forEach(x
                -> {
            if (x.getDeathTime() < DisplayManager.getCurrentTime()) {
                BeamsClient.getScene().getEntities().get(this.bulletModel).remove(x);
                BeamsClient.getScene().getLights().remove(x.getLight());
            } else {
                x.travel();
            }
        });
        this.bullets.removeIf(x -> x.getDeathTime() < DisplayManager.getCurrentTime());

    }

    private void gravitate() {
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        float verticalDistance = upwardsSpeed * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, verticalDistance, 0);
        float terrainHeight = BeamsClient.getScene().getTerrain().getHeightOfTerrain(super.getPosition().getX(), super.getPosition().getZ());
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
