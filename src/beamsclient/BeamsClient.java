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
import entity.texture.ModelTexture;
import entity.texture.TexturedModel;
import java.io.File;
import java.io.IOException;
import models.RawModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.MasterRenderer;
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

            masterRenderer.prepare();

            masterRenderer.render(guiManager.getGUI());

            masterRenderer.startTerrainRendering(scene);
            masterRenderer.render(scene.getTerrain());
            masterRenderer.stopTerrainRendering();
            
            masterRenderer.startEntityRendering(scene);
            masterRenderer.render(scene.getPlayer());
            masterRenderer.stopEntityRendering();

            MouseInput.checkInputs();
            checkForEscape();
            
            DisplayManager.updateDisplay();
        }

        Loader.cleanUp();
        masterRenderer.cleanUp();
        DisplayManager.closeDisplay();

    }

    private static void loadDefaultScene() throws IOException {
        
        RawModel model = OBJLoader.loadObjModel(new File("res/models/dragon.obj"));
        ModelTexture texture = new ModelTexture(Loader.loadTexture(new File("res/textures/texture.png")));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        texture.setReflectivity(1);
        texture.setShineDamper(100);
        Entity player = new Entity(texturedModel, new Vector3f(-40, 0, -20), new Vector3f(0, 0, 0), 1);
        
        scene = new Scene(
                player,
                new Camera(new Vector3f(-40, 10, 0), 10, 0, 0), 
                new Light(new Vector3f(50, 50, 50), new Vector3f(1, 1, 1)), 
                FileLoader.loadTerrain(new File("/Users/Blackened/NetBeansProjects/TerrainViewer/test.ter")));
    }
    
    private static void checkForEscape(){
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            keepRunning = false;
        }
    }

}
