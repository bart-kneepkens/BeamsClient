/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Light {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private Vector3f position;
    private Vector3f colour;
    private Vector3f attenuation = new Vector3f(1, 0, 0);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
    }

    public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
        this.position = position;
        this.colour = colour;
        this.attenuation = attenuation;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void increaseAttenuation(float x, float y, float z) {
        this.attenuation = this.attenuation.translate(this.attenuation.getX() + x, this.attenuation.getY() + y, this.attenuation.getZ() + z);
    }

    public void multiplyAttenuation(float x, float y, float z) {
        this.attenuation = new Vector3f(this.attenuation.getX() * x, this.attenuation.getY() * y, this.attenuation.getZ() * z);
    }

    /**
     * Increases the position of the entity.
     *
     * @param vector The vector that translates the position.
     * <b>Note:</b> this vector can not be normalized!
     */
    public void increasePosition(Vector3f vector) {
        this.position.x += vector.x;
        this.position.y += vector.y;
        this.position.z += vector.z;
    }
//</editor-fold>

}
