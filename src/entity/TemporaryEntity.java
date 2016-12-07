/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.texture.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class TemporaryEntity extends Entity {
    
    private long deathTime;
    private Vector3f travelDirection;
    private Light light;
    
    public TemporaryEntity(long creationTime, long duration, Vector3f travelDirection, TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
        this.deathTime = creationTime + duration;
        this.travelDirection = travelDirection;
        this.light = new Light(this.getPosition(), new Vector3f(1,1,1), new Vector3f(0.001f, 0.01f, 0.01f));
    }

    public long getDeathTime() {
        return deathTime;
    }
    
    public void travel(){
        this.increasePosition(travelDirection);
        //this.light.increasePosition(travelDirection);
    }

    public Light getLight() {
        return light;
    }
    
    
    
}
