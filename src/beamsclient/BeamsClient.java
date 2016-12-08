/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beamsClient;

import DataAccess.FileLoader;
import DataAccess.OBJLoader;
import GUI.UserInterface;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import DataAccess.lwjgl.Loader;
import Game.Camera;
import Game.Scene;
import entity.Entity;
import entity.Lamp;
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
import terrain.Terrain;
import userInput.KeyboardInput;
import userInput.MouseInput;

/**
 * This is a main class containing the program loop.
 *
 * @author Blackened
 */
public class BeamsClient {

    //<editor-fold defaultstate="collapsed" desc="Default files">
    private static final File DEFAULT_TERRAIN = new File("res/terrains/arenaTerrain/arenaTerrain.ter");
    private static final File DEFAULT_PLAYER_MODEL = new File("res/models/homo misluktus.obj");
    private static final File DEFAULT_PLAYER_TEXTURE = new File("res/textures/memeTexture.png");
    private static final File DEFAULT_OBJECT_MODEL = new File("res/models/target.obj");
    private static final File DEFAULT_OBJECT_TEXTURE = new File("res/textures/targetTexture.png");
    private static final File DEFAULT_LAMP_MODEL = new File("res/models/lamp.obj");
    private static final File DEFAULT_LAMP_TEXTURE = new File("res/textures/lampTexture.png");
    //</editor-fold>

    /**
     * Determines whether the program should be running. This variable is
     * checked every frame, and triggers a program close if false.
     */
    private static boolean keepRunning = true;

    /**
     * A reference to everything that should be rendered in 3D space, and
     * contains all logic for this 3D space.
     */
    private static Scene scene;

    /**
     * A reference to everything that should be rendered in 2D space, and
     * contains all logic for this 2D space.
     */
    private static UserInterface userInterface;

    /**
     * The main program loop.
     *
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        DisplayManager.createDisplay();

        loadDefaultUserInterface();
        loadDefaultScene();

        MasterRenderer masterRenderer = new MasterRenderer();

        //<editor-fold defaultstate="collapsed" desc="FPS Counter">
        long frameCount = 0;
        float fps;
        long lastTime = DisplayManager.getCurrentTime();
        //</editor-fold>

        while (!Display.isCloseRequested() && keepRunning) {
            scene.update();

            masterRenderer.prepare();

            masterRenderer.render(userInterface.getGUI());
            masterRenderer.render(scene);

            MouseInput.checkInputs();
            KeyboardInput.checkInputs();

            DisplayManager.updateDisplay();

            //<editor-fold defaultstate="collapsed" desc="FPS Counter">
            frameCount++;
            if (frameCount % 100 == 0) {
                fps = (DisplayManager.getCurrentTime() - lastTime) / 100;
                lastTime = DisplayManager.getCurrentTime();
                System.out.println(fps);
            }
            //</editor-fold>

        }

        Loader.cleanUp();
        masterRenderer.cleanUp();
        DisplayManager.closeDisplay();

    }

    /**
     * Gets the scene that is currently rendered to the display.
     *
     * @return Instance of scene that is currently rendered.
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * Gets the user interface that is currently rendered to the display.
     *
     * @return Instance of the user interface that is currently rendered.
     */
    public static UserInterface getUserInterface() {
        return userInterface;
    }

    /**
     * Sets the scene that will be rendered to the display.
     *
     * @param scene Instance of the scene that will be rendered.
     */
    public static void setScene(Scene scene) {
        BeamsClient.scene = scene;
    }

    /**
     * Gets the user interface that is currently rendered to the display.
     *
     * @param userInterface Instance of the user interface that will be
     * rendered.
     */
    public static void setUserInterface(UserInterface userInterface) {
        BeamsClient.userInterface = userInterface;
    }

    /**
     * Shuts down the program.
     */
    public static void exit() {
        keepRunning = false;
    }

    /**
     * Loads default terrain, player object, entities and lights to the scene.
     *
     * @throws IOException
     */
    private static void loadDefaultScene() throws IOException {

        //<editor-fold defaultstate="collapsed" desc="Terrain">
        // Loads the default terrain.
        Terrain terrain = FileLoader.loadTerrain(DEFAULT_TERRAIN);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Entities">
        // Loads the default player entity.
        RawModel playerModel = OBJLoader.loadObjModel(DEFAULT_PLAYER_MODEL);
        ModelTexture playerTexture = new ModelTexture(Loader.loadTexture(DEFAULT_PLAYER_TEXTURE));
        TexturedModel texturedPlayerModel = new TexturedModel(playerModel, playerTexture);
        playerTexture.setReflectivity(0);
        playerTexture.setShineDamper(100);
        Player player = new Player(
                texturedPlayerModel,
                new Vector3f(-5, 0, -32),
                new Vector3f(0, 0, 0),
                0.3f);

        // Loads the default textured object model.
        RawModel objectModel = OBJLoader.loadObjModel(DEFAULT_OBJECT_MODEL);
        ModelTexture objectTexture = new ModelTexture(Loader.loadTexture(DEFAULT_OBJECT_TEXTURE));
        TexturedModel texturedObjectModel = new TexturedModel(objectModel, objectTexture);
        objectTexture.setReflectivity(1);
        objectTexture.setShineDamper(100);

        // Loads the default three different entities with this object model.
        Entity entity = new Entity(
                texturedObjectModel,
                new Vector3f(-65, terrain.getHeightOfTerrain(-65, -25), -25f),
                new Vector3f(0, (float) Math.toRadians(135), 0),
                0.5f);
        Entity entity1 = new Entity(
                texturedObjectModel,
                new Vector3f(-55, terrain.getHeightOfTerrain(-55, -25), -25f),
                new Vector3f(0, (float) Math.toRadians(120), 0),
                0.5f);
        Entity entity2 = new Entity(
                texturedObjectModel,
                new Vector3f(-65, terrain.getHeightOfTerrain(-65, -15), -15f),
                new Vector3f(0, (float) Math.toRadians(150), 0),
                0.5f);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Lamps">
        // Loads the default textured lamp model.
        RawModel lampModel = OBJLoader.loadObjModel(DEFAULT_LAMP_MODEL).containsInvertedNormals();
        ModelTexture lampTexture = new ModelTexture(Loader.loadTexture(DEFAULT_LAMP_TEXTURE));
        TexturedModel texturedLampModel = new TexturedModel(lampModel, lampTexture);
        lampTexture.setReflectivity(1);
        lampTexture.setShineDamper(10);

        // Loads the default two different lamp entities with this lamp model.
        Lamp lamp = new Lamp(
                texturedLampModel,
                new Vector3f(-40, terrain.getHeightOfTerrain(-40, 0), 0),
                new Vector3f(0, 0, 0),
                0.5f,
                new Vector3f(0, 6.6f, 0),
                new Vector3f(1, 1, 0));

        Lamp lamp1 = new Lamp(
                texturedLampModel,
                new Vector3f(-67, terrain.getHeightOfTerrain(-67, -27), -27),
                new Vector3f(0, 0, 0), 
                0.5f,
                new Vector3f(0, 6.6f, 0),
                new Vector3f(1, 1, 0));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Lights">
        // Creates a default light that will light up the entire scene.
        Light sun = new Light(
                new Vector3f(-40, 50, 0),
                new Vector3f(1, 1, 1),
                new Vector3f(1f, 0.01f, 0.00001f));

        // Creates a list for all the lights in the scene, and adds the
        // light and the lights of the previously created lamps to that list.
        List<Light> lights = new ArrayList<>();
        lights.add(lamp.getLight());
        lights.add(lamp1.getLight());
        lights.add(sun);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Scene">
        // Creates the scene object with the player object, a new camera that
        // is relative to the player, the list of lights and the terrain.
        scene = new Scene(
                player,
                new Camera(player),
                lights,
                terrain);

        // Adds the object entities to the scene.
        scene.addEntity(entity);
        scene.addEntity(entity1);
        scene.addEntity(entity2);

        // Adds the lamp entities to the scene.
        scene.addEntity(lamp);
        scene.addEntity(lamp1);
        //</editor-fold>
    }

    /**
     * Loads default user interface object.
     */
    private static void loadDefaultUserInterface() throws IOException {

        //<editor-fold defaultstate="collapsed" desc="User Interface">
        // Loads the default user interface.
        userInterface = new UserInterface();
        //</editor-fold>

    }

}
