/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Camera {

    private Vector3f position = new Vector3f(-40, 100, 150);
    private float pitch = 30;
    private float yaw;
    private float roll;

    public Camera() {
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

    public void reset() {
        this.position = new Vector3f(-40, 100, 150);
        this.pitch = 30;
        this.yaw = 0;
        this.roll = 0;
    }

    public void turnHorizontally(float amount) {
        this.yaw += amount;

        float newX = 0;
        float newZ = 0;

        newX = (float) Math.sin(-yaw * (Math.PI / 180)) * 150 - 40;
        newZ = (float) Math.cos(-yaw * (Math.PI / 180)) * 150;

        this.position.setX(newX);
        this.position.setZ(newZ);
    }

}
