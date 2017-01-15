/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import entity.Entity;
import entity.Lamp;
import entity.Light;
import entity.Player;
import entity.texture.TexturedModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import terrain.Terrain;

/**
 *
 * @author Blackened
 */
public class Scene {

    private Camera camera;

    private List<Light> lights;

    private Player player;
    
    private Lamp sun;

    private Terrain terrain;
    
    Map<TexturedModel, List<Entity>> entities;

    public Lamp getSun() {
        return sun;
    }

    public void setSun(Lamp sun) {
        this.sun = sun;
        this.addEntity(sun);
        this.lights.add(sun.getLight());
    }

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
        this.entities = new HashMap<>();
    }

    public void update() {
        this.getPlayer().update();
        this.getCamera().update();
    }


    public void addEntity(Entity entity) {
        Optional<TexturedModel> optionalKey = this.entities.keySet().stream().filter(x -> x.doesEqual(entity.getModel())).findAny();
        if(optionalKey.isPresent())
        {
            this.entities.get(optionalKey.get()).add(entity);
        }
        else{
            List<Entity> entityList = new ArrayList<>();
            entityList.add(entity);
            this.entities.put(entity.getModel(), entityList);
        }
    }
    
    public void addTexturedModel(TexturedModel texturedModel){
        this.entities.put(texturedModel, new ArrayList<>());
    }
    
    public Map<TexturedModel, List<Entity>> getEntities() {
        return entities;
    }
    
    
}
