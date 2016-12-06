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

    private float distanceFromPlayer = 4;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;

    private Player player;

    public Camera(Vector3f position, float pitch, float yaw, float roll) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

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

    public void turnHorizontally(float amount) {
        this.yaw += amount;
    }

    public void turnVertically(float amount) {
        this.pitch += amount;
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.01f;
        if (distanceFromPlayer - zoomLevel > 1 && distanceFromPlayer - zoomLevel < 20) {
            distanceFromPlayer -= zoomLevel;
        }
    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(1) || Mouse.isButtonDown(0)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            if (pitch - pitchChange >= 0) {
                pitch -= pitchChange;
            }
        }
    }

    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(1)) {
            this.player.increaseRotation(new Vector3f(0, Mouse.getDX() * -0.003f, 0));
        }
        if (Mouse.isButtonDown(0)) {
            float angleChange = Mouse.getDX() * 0.003f;
            this.angleAroundPlayer -= angleChange;
        }
        else{
            angleAroundPlayer = 0;
        }
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch))) + 0.5f;
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRotation().getY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(theta));
        float offsetZ = (float) (horizontalDistance * Math.cos(theta));
        this.yaw = (float) (180 - Math.toDegrees(theta));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
    }

    public void move() {
        this.calculateZoom();
        this.calculatePitch();
        this.calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
    }

}
