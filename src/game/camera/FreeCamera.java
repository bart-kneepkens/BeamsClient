/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.camera;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class FreeCamera extends Camera{

    @Override
    public void update() {
        
    }

    public FreeCamera(Vector3f position, float pitch, float roll, float yaw) {
        this.position = position;
        this.pitch = pitch;
        this.roll = roll;
        this.yaw = yaw;
    }
    
    
    
}
