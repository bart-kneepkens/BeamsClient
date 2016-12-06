/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import entity.Light;
import org.lwjgl.util.vector.Vector3f;
import terrain.Terrain;

/**
 *
 * @author Blackened
 */
public class Scene {
    
    private Camera camera;
    
    private Light light;
    
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

    public Scene(Camera camera, Light light, Terrain terrain) {
        this.camera = camera;
        this.light = light;
        this.terrain = terrain;
    }
    
    
    
    
    
    
    
    
    
}
