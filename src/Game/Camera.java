/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import entity.Player;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Camera {

    private float distanceFromPlayer = 6;
    private float angleAroundPlayer = 0;

    private final Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private final float defaultPitch = 10;
    private float yaw = 0;
    private float roll;

    private Player player;

    public Camera(Player player) {
        this.player = player;
    }

    public void increasePosition(Vector3f amount) {
        this.position.x += amount.x;
        this.position.y += amount.y;
        this.position.z += amount.z;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

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

    private void calculatePitch() {
        if (Mouse.isButtonDown(1) || Mouse.isButtonDown(0)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            if (this.pitch - pitchChange > 0 && this.pitch - pitchChange < 90) {
                this.pitch -= pitchChange;
            }
        } else {
            if (this.pitch < this.defaultPitch - 2) {
                this.pitch += 2;
            } else if (this.pitch < this.defaultPitch) {
                this.pitch = this.defaultPitch;
            } else if (this.pitch > this.defaultPitch + 2) {
                this.pitch -= 2;
            } else if (this.pitch > this.defaultPitch) {
                this.pitch = this.defaultPitch;
            }
        }
    }

    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(0)) {
            this.angleAroundPlayer += Mouse.getDX() * -0.003f;
        } else {
            this.angleAroundPlayer = 0;
        }
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRotation().getY() + 0.2f + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(theta));
        float offsetZ = (float) (horizontalDistance * Math.cos(theta));
        this.yaw = (float) (180 - Math.toDegrees(theta - 0.2f));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        this.position.y = player.getPosition().getY() + 1 + verticalDistance;
    }

    public void update() {
        this.calculateZoom();
        this.calculatePitch();
        this.calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
    }

}
