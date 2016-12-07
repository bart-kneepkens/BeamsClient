/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import entity.Entity;
import entity.Light;
import entity.Player;
import java.util.ArrayList;
import java.util.List;
import terrain.Terrain;

/**
 *
 * @author Blackened
 */
public class Scene {

    private Camera camera;

    private List<Light> lights;

    private Player player;

    private Terrain terrain;
    
    List<Entity> entities;

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Camera getCamera() {
        return camera;
    }

    public List<Light> getLights() {
        return lights;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Player getPlayer() {
        return player;
    }

    public Scene(Player player, Camera camera, List<Light> lights, Terrain terrain) {
        this.player = player;
        this.camera = camera;
        this.lights = lights;
        this.terrain = terrain;
        this.entities = new ArrayList<>();
    }

    public void update() {
        this.getPlayer().gravitate();
        this.getCamera().move();
    }
    
    public void addEntity(Entity entity){
        this.entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    

}
