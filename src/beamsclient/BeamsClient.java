/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beamsClient;

import GUI.GUIManager;
import entities.Camera;
import entities.Light;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import GUI.lwjgl.GUIRenderer;
import GUI.shaders.GUIShader;
import renderEngine.Renderer;
import shaders.TerrainShader;
import toolbox.Maths;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class BeamsClient {

    public static boolean keepRunning = true;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        GUIShader shaderGUI = new GUIShader();
        GUIRenderer rendererGUI = new GUIRenderer();

        TerrainShader terrainShader = new TerrainShader();
        Renderer renderer = new Renderer(terrainShader);
        
        
        Camera camera = new Camera();
        Light light = new Light(new Vector3f(50, 50, 50), new Vector3f(1, 1, 1));
        GUIManager guiManager = new GUIManager(camera, loader);

        Matrix4f viewMatrix;

        while (!Display.isCloseRequested() && keepRunning) {
            rendererGUI.prepare();

            shaderGUI.start();
            guiManager.getAllGUIElements().forEach(x -> rendererGUI.render(x, shaderGUI));
            shaderGUI.stop();

            terrainShader.start();
            terrainShader.loadLight(light);
            viewMatrix = Maths.createViewMatrix(camera);
            terrainShader.loadUniformMatrix("viewMatrix", viewMatrix);
            renderer.render(guiManager.getTerrain());
            terrainShader.stop();
            
            

            MouseInput.checkInputs();

            DisplayManager.updateDisplay();
        }

        loader.cleanUp();
        shaderGUI.cleanUp();
        terrainShader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
