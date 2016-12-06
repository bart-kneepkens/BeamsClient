/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

import GUI.GUI;
import terrain.lwjgl.TerrainRenderer;
import GUI.lwjgl.GUIRenderer;
import entity.Entity;
import entity.lwjgl.EntityRenderer;
import terrain.Terrain;

/**
 *
 * @author Blackened
 */
public class MasterRenderer {
    
    private GUIRenderer guiRenderer;
    private EntityRenderer entityRenderer;
    private TerrainRenderer terrainRenderer;

    public MasterRenderer() {
        this.guiRenderer = new GUIRenderer();
    }
    
    
    
    public void render(GUI gui){
        guiRenderer.start();
        gui.getGuiElements().keySet().forEach(x -> {
            guiRenderer.render(x);
        });
        guiRenderer.stop();
    }
    
    public void render(Terrain terrain){
        
    }
    
    public void render(Entity entity){
        
    }
    
    public void cleanUp(){
        this.guiRenderer.cleanUp();
    }
    
}
