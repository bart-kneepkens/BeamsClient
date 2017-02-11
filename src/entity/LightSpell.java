/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public abstract class LightSpell extends Entity {
    
    public static long LAST_ONE_FIRED = 0;
    
    
    private final long deathTime;
    private Light light;
    
    public LightSpell(String name, TexturedModel model, long deathTime, Vector3f position, Vector3f rotation, float scale) {
        super(name, model, position, rotation, scale);
        this.deathTime = deathTime;
    }

    public final void setLight(Light light) {
        this.light = light;
    }
    
    
    
    public long getDeathTime() {
        return deathTime;
    }
    
    public abstract void update();

    public Light getLight() {
        return light;
    }
    
    
    
}
