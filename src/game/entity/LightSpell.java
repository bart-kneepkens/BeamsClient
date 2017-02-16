/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity;

import game.entity.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public abstract class LightSpell extends Entity {

    //<editor-fold defaultstate="collapsed" desc="Static Properties">
    public static long LAST_ONE_FIRED = 0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private final long deathTime;
    private Light light;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public final void setLight(Light light) {
        this.light = light;
    }

    public long getDeathTime() {
        return deathTime;
    }

    public Light getLight() {
        return light;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public LightSpell(String name, TexturedModel model, long deathTime, Vector3f position, Vector3f rotation, float scale) {
        super(name, model, position, rotation, scale);
        this.deathTime = deathTime;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public abstract void update();
//</editor-fold>

}
