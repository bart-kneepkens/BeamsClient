/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.camera;

import game.entity.Player;
import org.lwjgl.input.Mouse;

/**
 * Camera class for a camera that follows a player object around.
 * @author Blackened
 */
public class ThirdPersonCamera extends Camera{

    /**
     * The distance from the camera to the player object.
     */
    private float distanceFromPlayer = 6;
    
    /**
     * The horizontal angle of the camera around the player object.
     */
    private float angleAroundPlayer = 0;

    /**
     * The player object that this camera is attached to.
     */
    private Player player;
    
    /**
     * Creates a new instance of the ThirdPersonCamera class.
     * @param player The player object the camera object will be 'attached' to 
     * <i>(i.e. following)</i>.
     */
    public ThirdPersonCamera(Player player) {
        this.player = player;
    }

    /**
     * Checks for mouse scrolling and adjusts the zoom level.
     */
    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.001f;
        if (zoomLevel > 0) {
            if (distanceFromPlayer > 4) {
                distanceFromPlayer -= zoomLevel;
            }
        } else {
            if (distanceFromPlayer < 10) {
                distanceFromPlayer -= zoomLevel;
            }
        }
    }

    /**
     * Checks for mouse input, and sets the pitch of this camera accordingly.
     * If there is no current mouse input for changing pitch, and the pitch is 
     * some amount smaller than the minimum pitch, the pitch will change towards 
     * the desired minimum pitch.
     */
    private void calculatePitch() {
        if (Mouse.isButtonDown(1) || Mouse.isButtonDown(0)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            if (this.pitch - pitchChange > 0 && this.pitch - pitchChange < 90) {
                this.pitch -= pitchChange;
            }
        } else {
            if (this.pitch < MINIMUM_PITCH - 2) {
                this.pitch += 2;
            } else if (this.pitch < MINIMUM_PITCH) {
                this.pitch = MINIMUM_PITCH;
            } else if (this.pitch > MINIMUM_PITCH + 2) {
                this.pitch -= 2;
            } else if (this.pitch > MINIMUM_PITCH) {
                this.pitch = MINIMUM_PITCH;
            }
        }
    }

    /**
     * Checks for mouse input, and sets the angle around the player object
     * accordingly.
     */
    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(0)) {
            this.angleAroundPlayer += Mouse.getDX() * -0.003f;
            this.angleAroundPlayer = this.angleAroundPlayer % ((float)(Math.PI * 2));
            
        } else {
            if (this.angleAroundPlayer < -0.075) {
                this.angleAroundPlayer += 0.075;
            } else if (this.angleAroundPlayer < 0) {
                this.angleAroundPlayer = 0;
            } else if (this.angleAroundPlayer > 0.075) {
                this.angleAroundPlayer -= 0.075;
            } else if (this.angleAroundPlayer > 0) {
                this.angleAroundPlayer = 0;
            }
        }
    }

    /**
     * Calculates the exact horizontal distance between this camera and the 
     * player object.
     * @return The horizontal distance between this camera and the player 
     * object.
     */
    private float getHorizontalOffSet() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    /**
     * Calculates the exact vertical distance between this camera and the 
     * player object.
     * @return The vertical distance between this camera and the player object.
     */
    private float getVerticalOffSet() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    /**
     * Sets the position for this camera based on a given horizontal offset and 
     * vertical offset.
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

    @Override
    public void update() {
        this.calculateZoom();
        this.calculatePitch();
        this.calculateAngleAroundPlayer();
        float horizontalOffSet = getHorizontalOffSet();
        float verticalOffSet = getVerticalOffSet();
        this.calculateCameraPosition(horizontalOffSet, verticalOffSet);
    }

}
