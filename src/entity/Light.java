/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Light {

    private Vector3f position;
    private Vector3f colour;
    private Vector3f attenuation = new Vector3f(1, 0, 0);

    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
    }

    public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
        this.position = position;
        this.colour = colour;
        this.attenuation = attenuation;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    /**
     * Increases the position of the entity.
     *
     * @param vector The vector that translates the position.
     * @NOTE this vector can not be normalized!
     */
    public void increasePosition(Vector3f vector) {
        this.position.x += vector.x;
        this.position.y += vector.y;
        this.position.z += vector.z;
    }
}
