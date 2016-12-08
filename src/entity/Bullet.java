/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.texture.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 *
 * @author Blackened
 */
public class Bullet extends Entity {
    
    public static long LAST_ONE_FIRED = 0;
    private static final float SPEED = 100;
    
    private final long deathTime;
    private final Vector3f travelDirection;
    private final Light light;
    
    public Bullet(long creationTime, long duration, Vector3f travelDirection, TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
        this.deathTime = creationTime + duration;
        this.travelDirection = new Vector3f();
        travelDirection.normalise(this.travelDirection);
        this.light = new Light(this.getPosition(), new Vector3f(1,1,1), new Vector3f(0.001f, 0.01f, 0.005f));
    }

    public long getDeathTime() {
        return deathTime;
    }
    
    public void travel(){
        this.increasePosition(travelDirection.getX() * DisplayManager.getFrameTimeSeconds() * SPEED, travelDirection.getY(), travelDirection.getZ() * DisplayManager.getFrameTimeSeconds() * SPEED);
    }

    public Light getLight() {
        return light;
    }
    
    
    
}
