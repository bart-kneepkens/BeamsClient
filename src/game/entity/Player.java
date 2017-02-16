/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity;

import beamsClient.BeamsClient;
import game.entity.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 *
 * @author Blackened
 */
public abstract class Player extends Entity {

    //<editor-fold defaultstate="collapsed" desc="Static Properties">
    private static final float GRAVITY = -50f;
    private static final float JUMP_POWER = 10;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private final float movementSpeed = 10f;
    private final float turnSpeed = 4f;
    private float upwardsSpeed = 0;

    private LightSpell activeSpell;

    private boolean isInAir = true;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public LightSpell getActiveSpell() {
        return activeSpell;
    }

    public void setActiveSpell(LightSpell activeSpell) {
        this.activeSpell = activeSpell;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Instantiates a new object of type Player.
     *
     * @param model
     * @param position
     * @param rotation
     * @param scale
     */
    public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super("Player", model, position, rotation, scale);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public final void jump() {
        if (!this.isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            this.isInAir = true;
        }
    }

    public final void turnLeft(float amount) {
        super.increaseRotation(0, 1 * turnSpeed * amount, 0);
    }

    public final void update() {
        this.gravitate();
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

    /**
     * Moves this player object forward.
     *
     * @param amount Should represent the amount that needs to be moved forward.
     */
    public final void moveForward(float amount) {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY())) * amount;
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY())) * amount;
        Vector3f nextPosition = super.calculateTranslation(dx, 0, dz);
        if (isMovementAllowed(nextPosition)) {
            super.increasePosition(dx, 0, dz);
        }
    }

    /**
     * Moves this player object backwards.
     *
     * @param amount Should represent the amount that needs to be moved
     * backwards.
     */
    public final void moveBackwards(float amount) {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY())) * amount;
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY())) * amount;
        Vector3f nextPosition = super.calculateTranslation(-dx, 0, -dz);
        if (isMovementAllowed(nextPosition)) {
            super.increasePosition(-dx, 0, -dz);
        }
    }

    /**
     * Moves this player object to the relative left of the object itself.
     *
     * @param amount Should represent the amount that needs to be moved left.
     */
    public final void strafeLeft(float amount) {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY() - 0.5 * Math.PI)) * amount;
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY() - 0.5 * Math.PI)) * amount;
        Vector3f nextPosition = super.calculateTranslation(-dx, 0, -dz);
        if (isMovementAllowed(nextPosition)) {
            super.increasePosition(-dx, 0, -dz);
        }
    }

    /**
     * Moves this player object to the relative right of the object itself.
     *
     * @param amount Should represent the amount that needs to be moved right.
     */
    public final void strafeRight(float amount) {
        float dx = (float) (movementSpeed * Math.sin(super.getRotation().getY() + 0.5 * Math.PI)) * amount;
        float dz = (float) (movementSpeed * Math.cos(super.getRotation().getY() + 0.5 * Math.PI)) * amount;
        Vector3f nextPosition = super.calculateTranslation(-dx, 0, -dz);
        if (isMovementAllowed(nextPosition)) {
            super.increasePosition(-dx, 0, -dz);
        }
    }

    public abstract void castSpell1();

    public abstract void castSpell2();

    public abstract void castSpell3();

    public abstract void castSpell4();

    public abstract void castSpell5();

    public abstract void castSpell6();

    public abstract void castSpell7();

    public final void turnRight(float amount) {
        super.increaseRotation(0, -1 * turnSpeed * amount, 0);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    private final boolean isMovementAllowed(Vector3f nextPosition) {
        return (BeamsClient.getInstance().getScene().getTerrain().getHeightOfTerrain(nextPosition.getX(), nextPosition.getZ()) - this.getPosition().getY() < 0.3) && !this.checkCollisions(nextPosition);
    }

    private final void gravitate() {
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
//</editor-fold>

}
