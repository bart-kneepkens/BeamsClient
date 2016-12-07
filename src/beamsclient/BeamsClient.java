/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beamsClient;

import DataAccess.FileLoader;
import DataAccess.OBJLoader;
import GUI.GUIManager;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import DataAccess.lwjgl.Loader;
import Game.Camera;
import Game.Scene;
import entity.Entity;
import entity.Light;
import entity.Player;
import entity.texture.ModelTexture;
import entity.texture.TexturedModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.RawModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.MasterRenderer;
import userInput.KeyboardInput;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class BeamsClient {

    public static boolean keepRunning = true;
    public static Scene scene;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        DisplayManager.createDisplay();
        
        loadDefaultScene();

        MasterRenderer masterRenderer = new MasterRenderer();

        GUIManager guiManager = new GUIManager();

        while (!Display.isCloseRequested() && keepRunning) {
            scene.update();
            
            masterRenderer.prepare();

            masterRenderer.render(guiManager.getGUI());

            masterRenderer.render(scene);

            MouseInput.checkInputs();
            KeyboardInput.checkInputs();
            
            DisplayManager.updateDisplay();
        }

        Loader.cleanUp();
        masterRenderer.cleanUp();
        DisplayManager.closeDisplay();

    }

    private static void loadDefaultScene() throws IOException {
        
        RawModel model = OBJLoader.loadObjModel(new File("res/models/stanfordBunny.obj"));
        ModelTexture texture = new ModelTexture(Loader.loadTexture(new File("res/textures/texture.png")));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        texture.setReflectivity(1);
        texture.setShineDamper(100);
        Player player = new Player(texturedModel, new Vector3f(-5, 0, -32), new Vector3f(0, 0, 0), 0.1f);
        
        RawModel model1 = OBJLoader.loadObjModel(new File("res/models/target.obj"));
        ModelTexture texture1 = new ModelTexture(Loader.loadTexture(new File("res/textures/targetTexture.png")));
        TexturedModel texturedModel1 = new TexturedModel(model1, texture1);
        texture1.setReflectivity(1);
        texture1.setShineDamper(100);
        
        Light sun = new Light(new Vector3f(-40,50,0), new Vector3f(1,1,1), new Vector3f(1f, 0.01f, 0.00001f));
        Light light2 = new Light(new Vector3f(-11,20,-25), new Vector3f(1.5f,1,1), new Vector3f(1f, 0.006f, 0.001f));
        Light light3 = new Light(new Vector3f(-67,20,27), new Vector3f(1.5f,1,1), new Vector3f(1f, 0.006f, 0.001f));
        Light light4 = new Light(new Vector3f(-60,10,-19), new Vector3f(2,2,0), new Vector3f(0.001f, 0.01f, 0.01f));
        
        List<Light> lights = new ArrayList<>();
        lights.add(sun);
        lights.add(light2);
        lights.add(light3);
        lights.add(light4);
        
        
        
        scene = new Scene(
                player,
                new Camera(player), 
                lights, 
                FileLoader.loadTerrain(new File("res/terrains/arenaTerrain/arenaTerrain.ter")));
        
        Entity entity = new Entity(texturedModel1, new Vector3f(-65, scene.getTerrain().getHeightOfTerrain(-65, -25), -25f), new Vector3f(0, (float) Math.toRadians(135), 0), 1f);
        Entity entity1 = new Entity(texturedModel1, new Vector3f(-55, scene.getTerrain().getHeightOfTerrain(-55, -25), -25f), new Vector3f(0, (float) Math.toRadians(120), 0), 1f);
        Entity entity2 = new Entity(texturedModel1, new Vector3f(-65, scene.getTerrain().getHeightOfTerrain(-65, -15), -15f), new Vector3f(0, (float) Math.toRadians(150), 0), 1f);
        scene.addEntity(entity);
        scene.addEntity(entity1);
        scene.addEntity(entity2);
    }

}
