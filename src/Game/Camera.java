/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Camera {

    private Vector3f position;
    private float pitch;
    private float yaw;
    private float roll;

    
    public Camera(Vector3f position, float pitch, float yaw, float roll) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
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
    
    public void turnVertically(float amount){
        this.pitch += amount;
    }

}
