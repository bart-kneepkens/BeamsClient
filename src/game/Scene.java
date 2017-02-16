/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.camera.Camera;
import game.entity.Entity;
import game.entity.Lamp;
import game.entity.Light;
import game.entity.Player;
import game.entity.models.TexturedModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import game.terrain.Terrain;

/**
 *
 * @author Blackened
 */
public class Scene {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     * The camera that will be used to render the scene.
     */
    private Camera camera;

    /**
     * A list of all lights present in the scene.
     */
    private List<Light> lights;

    /**
     * The player object of the scene.
     */
    private Player player;

    /**
     * The sun of the scene.
     */
    private Lamp sun;

    /**
     * The terrain of the scene.
     */
    private Terrain terrain;

    /**
     * A map of all entities present in the scene, mapped to their textured
     * model.
     */
    private Map<TexturedModel, List<Entity>> entities;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    /**
     * Getter for the sun of this scene object.
     *
     * @return The sun.
     */
    public Lamp getSun() {
        return sun;
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

    public Map<TexturedModel, List<Entity>> getEntities() {
        return entities;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Scene(Player player, Camera camera, List<Light> lights, Terrain terrain) {
        this.player = player;
        this.camera = camera;
        this.lights = lights;
        this.terrain = terrain;
        this.entities = new HashMap<>();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     *
     * @param sun
     */
    public void setSun(Lamp sun) {
        this.sun = sun;
        this.addEntity(sun);
        this.lights.add(sun.getLight());
    }

    public void update() {
        this.getPlayer().update();
        this.getCamera().update();
    }

    public void addEntity(Entity entity) {
        Optional<TexturedModel> optionalKey = this.entities.keySet().stream().filter(x -> x.doesEqual(entity.getModel())).findAny();
        if (optionalKey.isPresent()) {
            this.entities.get(optionalKey.get()).add(entity);
        } else {
            List<Entity> entityList = new ArrayList<>();
            entityList.add(entity);
            this.entities.put(entity.getModel(), entityList);
        }
    }

    public void addTexturedModel(TexturedModel texturedModel) {
        this.entities.put(texturedModel, new ArrayList<>());
    }
//</editor-fold>

}
