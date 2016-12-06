/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import entity.Light;
import terrain.Terrain;

/**
 *
 * @author Blackened
 */
public class Scene {
    
    private Camera camera;
    
    private Light light;
    
    private Terrain terrain;
    
    
    public void resetCamera(){
        this.camera.reset();
    }
    
}
