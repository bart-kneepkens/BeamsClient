/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity;

import game.entity.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 *
 * @author Blackened
 */
public class Bullet extends LightSpell {

    //<editor-fold defaultstate="collapsed" desc="Static Properties">
    private static final float SPEED = 100;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private final Vector3f travelDirection;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Bullet(String name, long creationTime, long duration, Vector3f travelDirection, TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(name, model, creationTime + duration, position, rotation, scale);
        this.travelDirection = new Vector3f();
        travelDirection.normalise(this.travelDirection);
        super.setLight(new Light(this.getPosition(), new Vector3f(1, 1, 1), new Vector3f(0.001f, 0.01f, 0.005f)));
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public void update() {
        this.checkCollisions();
        this.increasePosition(travelDirection.getX() * DisplayManager.getFrameTimeSeconds() * SPEED, travelDirection.getY(), travelDirection.getZ() * DisplayManager.getFrameTimeSeconds() * SPEED);
    }
//</editor-fold>

}
