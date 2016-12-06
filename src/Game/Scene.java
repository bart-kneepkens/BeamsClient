/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import entity.Entity;
import entity.Light;
import terrain.Terrain;

/**
 *
 * @author Blackened
 */
public class Scene {
    
    private Camera camera;
    
    private Light light;
    
    private Entity player;
    
    private Terrain terrain;

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Camera getCamera() {
        return camera;
    }

    public Light getLight() {
        return light;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Entity getPlayer() {
        return player;
    }

    
    public Scene(Entity player, Camera camera, Light light, Terrain terrain) {
        this.player = player;
        this.camera = camera;
        this.light = light;
        this.terrain = terrain;
    }
    
    
    
    
    
    
    
    
    
}
