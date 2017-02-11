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
public class Lamp extends Entity {

    private final Light light;

    public Lamp(String name, TexturedModel model, Vector3f position, Vector3f rotation, float scale, Vector3f lightOffSet, Vector3f lightColour) {
        super(name, model, position, rotation, scale);
        this.light = new Light(
                new Vector3f(super.getPosition().getX() + lightOffSet.getX() * scale,
                        super.getPosition().getY() + lightOffSet.getY() * scale,
                        super.getPosition().getZ() + lightOffSet.getZ() * scale),
                lightColour,
                new Vector3f(1f, 0.01f, 0.001f));
    }

    public Lamp(String name, TexturedModel model, Vector3f position, Vector3f rotation, float scale, Vector3f lightOffSet, Vector3f lightColour, Vector3f attenuation) {
        super(name, model, position, rotation, scale);
        this.light = new Light(
                new Vector3f(super.getPosition().getX() + lightOffSet.getX() * scale,
                        super.getPosition().getY() + lightOffSet.getY() * scale,
                        super.getPosition().getZ() + lightOffSet.getZ() * scale),
                lightColour,
                attenuation);
    }

    public Light getLight() {
        return light;
    }
    
    public void decreaseAttenuation(){
        this.light.multiplyAttenuation(1, 0.95f, 1);
    }

}
