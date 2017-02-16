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
public class Halo extends LightSpell {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private float attenuation_a = 0.2f;
    private float attenuation_b = -0.08f;
    private float attenuation_c = -0.1f;

    private float r = 0.5f;
    private float g = 1f;
    private float b = 0.5f;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Halo(String name, long creationTime, long duration, TexturedModel model, Vector3f position, Vector3f rotation) {
        super(name, model, creationTime + duration, position, rotation, 1);

        super.setLight(new Light(this.getPosition(), new Vector3f(0.1f, 0.1f, 0.1f), new Vector3f(0.2f, -0.08f, -0.1f)));
        //this.setModelScale();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public void update() {
        attenuation_a = attenuation_a * 1.05f;
        attenuation_b = attenuation_b * 0.01f;
        attenuation_c = attenuation_c * 0.95f;
        b *= 1.01f;
        //setModelScale();
        //super.increaseRotation(new Vector3f(0.1f,0.1f,0.1f));
        this.getLight().setColour(new Vector3f(r, g, b));
        this.getLight().setAttenuation(new Vector3f(attenuation_a, attenuation_b, attenuation_c));
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    private void setModelScale() {
        float scale_solution1;
        scale_solution1 = (float) ((-attenuation_b + Math.sqrt((attenuation_b * attenuation_b) - (4 * attenuation_a * attenuation_c))) / (2 * attenuation_a));
        super.setScale(1 / scale_solution1);
    }
//</editor-fold>

}
