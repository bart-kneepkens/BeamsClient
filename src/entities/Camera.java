/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Camera {
    
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
    }
    
    public void move(){
        Keyboard.enableRepeatEvents(false);
        boolean pressed = Keyboard.getEventKeyState();
        if(pressed){
            System.out.println("w pressed");
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z -= 0.02f;
        }
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
    
    public void reset(){
        this.position = new Vector3f(0,0,0);
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
    }
    
    
    
    
    
    
}
