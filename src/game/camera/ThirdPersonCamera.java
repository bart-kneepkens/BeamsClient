/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.camera;

import game.entity.Player;

/**
 * Camera class for a camera that follows a player object around.
 *
 * @author Blackened
 */
public class ThirdPersonCamera extends Camera {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     * The normal pitch for this camera object.
     */
    private float normalPitch = 10;

    /**
     * The normal angle around player for this camera object.
     */
    private float normalAngleAroundPlayer = 0;

    /**
     * The distance from the camera to the player object.
     */
    private float distanceFromPlayer = 6;

    /**
     * The horizontal angle of the camera around the player object.
     */
    private float angleAroundPlayer = 0;

    /**
     * Monitors whether the pitch has changed this frame.
     */
    private boolean pitchChanged = false;

    /**
     * Monitors whether the yaw has changed this frame.
     */
    private boolean yawChanged = false;

    /**
     * The player object that this camera is attached to.
     */
    private Player player;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new instance of the ThirdPersonCamera class.
     *
     * @param player The player object the camera object will be 'attached' to
     * <i>(i.e. following)</i>.
     */
    public ThirdPersonCamera(Player player) {
        this.player = player;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Adjusts the zoom level.
     *
     * @param amount
     */
    @Override
    public void changeZoomLevel(float amount) {
        if (amount > 0) {
            if (distanceFromPlayer - amount > 4) {
                distanceFromPlayer -= amount;
            }
        } else {
            if (distanceFromPlayer - amount < 10) {
                distanceFromPlayer -= amount;
            }
        }
    }

    @Override
    public void changeYaw(float amount) {
        this.yawChanged = true;
        this.angleAroundPlayer += amount;
        this.angleAroundPlayer = this.angleAroundPlayer % ((float) (Math.PI * 2));
    }

    @Override
    public void changePitch(float amount) {
        this.pitchChanged = true;
        if (this.pitch - amount > 0 && this.pitch - amount < 90) {
            this.pitch -= amount;
        }
    }

    @Override
    public void update() {
        if (!this.pitchChanged) {
            this.normalizePitch();
        }
        if (!this.yawChanged) {
            this.normalizeAngleAroundPlayer();
        }

        float horizontalOffSet = getHorizontalOffSet();
        float verticalOffSet = getVerticalOffSet();
        this.calculateCameraPosition(horizontalOffSet, verticalOffSet);

        this.pitchChanged = false;
        this.yawChanged = false;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Calculates the exact horizontal distance between this camera and the
     * player object.
     *
     * @return The horizontal distance between this camera and the player
     * object.
     */
    private float getHorizontalOffSet() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    /**
     * Calculates the exact vertical distance between this camera and the player
     * object.
     *
     * @return The vertical distance between this camera and the player object.
     */
    private float getVerticalOffSet() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    /**
     * Normalizes the pitch by moving it towards the normal pitch value.
     */
    private void normalizePitch() {
        if (this.pitch < this.normalPitch - 2) {
            this.pitch += 2;
        } else if (this.pitch < this.normalPitch) {
            this.pitch = this.normalPitch;
        } else if (this.pitch > this.normalPitch + 2) {
            this.pitch -= 2;
        } else if (this.pitch > this.normalPitch) {
            this.pitch = this.normalPitch;
        }
    }

    /**
     * Normalizes the angle around the player by moving it towards the normal
     * angle around player value.
     */
    private void normalizeAngleAroundPlayer() {
        if (this.angleAroundPlayer < -0.075) {
            this.angleAroundPlayer += 0.075;
        } else if (this.angleAroundPlayer < this.normalAngleAroundPlayer) {
            this.angleAroundPlayer = this.normalAngleAroundPlayer;
        } else if (this.angleAroundPlayer > 0.075) {
            this.angleAroundPlayer -= 0.075;
        } else if (this.angleAroundPlayer > this.normalAngleAroundPlayer) {
            this.angleAroundPlayer = this.normalAngleAroundPlayer;
        }
    }

    /**
     * Sets the position for this camera based on a given horizontal offset and
     * vertical offset.
     *
     * @param horizontalOffSet The horizontal distance between this camera
     * object and the player object.
     * @param verticalOffSet The vertical distance between this camera object
     * and the player object.
     */
    private void calculateCameraPosition(float horizontalOffSet, float verticalOffSet) {
        float theta = player.getRotation().getY() + 0.2f + angleAroundPlayer;
        float offsetX = (float) (horizontalOffSet * Math.sin(theta));
        float offsetZ = (float) (horizontalOffSet * Math.cos(theta));
        this.yaw = (float) (180 - Math.toDegrees(theta - 0.2f));
        this.position.x = player.getPosition().x - offsetX;
        this.position.z = player.getPosition().z - offsetZ;
        this.position.y = player.getPosition().getY() + 1 + verticalOffSet;
    }
//</editor-fold>

}
