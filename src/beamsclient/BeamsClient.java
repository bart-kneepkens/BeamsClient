/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beamsClient;

import DataAccess.FileLoader;
import GUI.GUIManager;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import DataAccess.lwjgl.Loader;
import Game.Camera;
import Game.Scene;
import entity.Light;
import java.io.File;
import java.io.IOException;
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
     */
    public static void main(String[] args) throws IOException {
        DisplayManager.createDisplay();
        
        loadDefaultScene();

        MasterRenderer masterRenderer = new MasterRenderer();

        GUIManager guiManager = new GUIManager();

        while (!Display.isCloseRequested() && keepRunning) {

            masterRenderer.prepare();

            masterRenderer.render(guiManager.getGUI());

            masterRenderer.start3DRendering(scene);
            masterRenderer.render(scene.getTerrain());
            masterRenderer.stop3DRendering();

            MouseInput.checkInputs();
            
            DisplayManager.updateDisplay();
        }

        Loader.cleanUp();
        masterRenderer.cleanUp();
        DisplayManager.closeDisplay();

    }

    private static void loadDefaultScene() throws IOException {
        scene = new Scene(new Camera(new Vector3f(-40, 10, 0), 10, 0, 0), 
                new Light(new Vector3f(50, 50, 50), new Vector3f(1, 1, 1)), 
                FileLoader.loadTerrain(new File("/Users/Blackened/NetBeansProjects/TerrainViewer/test.ter")));
    }

}
