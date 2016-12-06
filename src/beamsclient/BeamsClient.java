/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beamsClient;

import GUI.GUIManager;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import Game.Scene;
import renderEngine.MasterRenderer;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class BeamsClient {

    public static boolean keepRunning = true;
    public static Scene scene = new Scene();
    public static Loader loader = new Loader();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DisplayManager.createDisplay();

        
        
        MasterRenderer masterRenderer = new MasterRenderer();
        
        GUIManager guiManager = new GUIManager(loader);

        while (!Display.isCloseRequested() && keepRunning) {

            masterRenderer.render(guiManager.getGUI());            

            MouseInput.checkInputs();

            DisplayManager.updateDisplay();
        }

        loader.cleanUp();
        masterRenderer.cleanUp();
        DisplayManager.closeDisplay();

    }

}
