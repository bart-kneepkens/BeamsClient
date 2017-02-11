/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beamsClient.BeamsClient;
import entity.models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

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

    private LightSpell activeSpell;

    private TexturedModel bulletModel;

    private boolean isInAir = true;

    public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale, TexturedModel bulletModel) {
        super("Player", model, position, rotation, scale);
        this.bulletModel = bulletModel;
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
        if (this.activeSpell == null) {
            LightSpell bullet = new Bullet("bullet " + DisplayManager.getCurrentTime(),
                    DisplayManager.getCurrentTime(),
                    1000,
                    new Vector3f((float) Math.sin(this.getRotation().getY()) * 2,
                            0,
                            (float) Math.cos(this.getRotation().getY()) * 2),
                    bulletModel,
                    new Vector3f(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ()),
                    new Vector3f(this.getRotation().getX(), this.getRotation().getY(), this.getRotation().getZ()),
                    0.2f);
            BeamsClient.getInstance().getScene().addEntity(bullet);
            BeamsClient.getInstance().getScene().getLights().add(bullet.getLight());

            this.activeSpell = bullet;
            LightSpell.LAST_ONE_FIRED = DisplayManager.getCurrentTime();
        }
    }

    public void fireHalo() {
        if (this.activeSpell == null) {
            LightSpell halo = new Halo("halo" + DisplayManager.getCurrentTime(),
                    DisplayManager.getCurrentTime(),
                    2000,
                    null,
                    new Vector3f(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ()),
                    new Vector3f(0, 0, 0));
            BeamsClient.getInstance().getScene().getLights().add(halo.getLight());
            this.activeSpell = halo;
            LightSpell.LAST_ONE_FIRED = DisplayManager.getCurrentTime();
        }
    }

    private boolean isMovementAllowed(Vector3f nextPosition) {
        
        return (BeamsClient.getInstance().getScene().getTerrain().getHeightOfTerrain(nextPosition.getX(), nextPosition.getZ()) - this.getPosition().getY() < 0.3) && !this.checkCollisions(nextPosition);
    }

    public void turnRight() {
        super.increaseRotation(new Vector3f(0, -1 * turnSpeed * DisplayManager.getFrameTimeSeconds(), 0));
    }

    public void turnLeft() {
        super.increaseRotation(new Vector3f(0, 1 * turnSpeed * DisplayManager.getFrameTimeSeconds(), 0));
    }

    public void update() {
        this.gravitate();
        this.checkInputs();
        this.checkCollisions();
        if (this.activeSpell != null) {
            if (this.activeSpell.getDeathTime() < DisplayManager.getCurrentTime()) {
                BeamsClient.getInstance().getScene().getEntities().remove(activeSpell.getModel());
                BeamsClient.getInstance().getScene().getLights().remove(this.activeSpell.getLight());
                this.activeSpell = null;
            } else {
                this.activeSpell.update();
            }
        }
    }

    private void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W) || Mouse.isButtonDown(0) && Mouse.isButtonDown(1)) {
            this.moveForward();
            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                this.moveForward();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.moveBackward();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (Mouse.isButtonDown(1)) {
                this.strafeRight();
            } else {
                this.turnRight();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (Mouse.isButtonDown(1)) {
                this.strafeLeft();
            } else {
                this.turnLeft();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            this.strafeLeft();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            this.strafeRight();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            this.jump();
        }
        if (Mouse.isButtonDown(1)) {
            this.increaseRotation(new Vector3f(0, Mouse.getDX() * -0.003f, 0));
        }
    }

    private void gravitate() {
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        float verticalDistance = upwardsSpeed * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, verticalDistance, 0);
        float terrainHeight = BeamsClient.getInstance().getScene().getTerrain().getHeightOfTerrain(super.getPosition().getX(), super.getPosition().getZ());
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
