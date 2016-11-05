/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beamsclient;

import DataAccess.ModelData;
import GUI.GUIManager;
import entities.Camera;
import entities.Entity;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Renderer;
import renderEngine.GUIRenderer;
import shaders.GUI.GUIShader;
import shaders.StaticShader;
import textures.ModelTexture;
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
        StaticShader shader = new StaticShader();
        GUIShader shaderGUI = new GUIShader();
        Renderer renderer = new Renderer(shader);
        GUIRenderer rendererGUI = new GUIRenderer();

        // <editor-fold>
        float[] vertices = {
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f

        };

        float[] textureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0

        };

        int[] indices = {
            0, 1, 3,
            3, 1, 2,
            4, 5, 7,
            7, 5, 6,
            8, 9, 11,
            11, 9, 10,
            12, 13, 15,
            15, 13, 14,
            16, 17, 19,
            19, 17, 18,
            20, 21, 23,
            23, 21, 22

        };
        // </editor-fold>

        RawModel model = loader.loadToVAO(new ModelData(vertices, textureCoords, indices));
        ModelTexture texture = new ModelTexture(loader.loadTexture("texture"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -5), new Vector3f(0, 0, 0), 1);
        

        Camera camera = new Camera();
        GUIManager guiManager = new GUIManager(camera);

        Matrix4f viewMatrix;

        while (!Display.isCloseRequested() && keepRunning) {
            rendererGUI.prepare();
            camera.move();
            entity.increaseRotation(new Vector3f(0.01f, 0.01f, 0));
            //entityButton.increasePosition(new Vector3f(0,0,0.1f));
            // game logic
            shader.start();
            viewMatrix = Maths.createViewMatrix(camera);
            shader.loadUniformMatrix("viewMatrix", viewMatrix);
            renderer.render(entity, shader);
            shader.stop();

            shaderGUI.start();
            guiManager.getAllButtons().forEach(x -> rendererGUI.render(x, shaderGUI));
            shaderGUI.stop();
            MouseInput.checkInputs();

            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
