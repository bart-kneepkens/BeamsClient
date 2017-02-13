/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beamsClient;

import gui.UserInterface;
import renderEngine.DisplayManager;
import dataAccess.lwjgl.Loader;
import game.camera.ThirdPersonCamera;
import game.Scene;
import dataAccess.FileLoader;
import dataAccess.OBJLoader;
import game.camera.FreeCamera;
import game.entity.Entity;
import game.entity.Lamp;
import game.entity.Light;
import game.entity.Player;
import game.entity.models.ModelTexture;
import game.entity.models.TexturedModel;
import gui.font.lwjgl.TextMaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import game.entity.models.RawModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.MasterRenderer;
import game.terrain.Terrain;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import userInput.KeyboardInput;
import userInput.MouseInput;

/**
 * This is a main class containing the program loop.
 *
 * @author Blackened
 */
public class BeamsClient extends GameApplication {

    //<editor-fold defaultstate="collapsed" desc="Default files">
    private final File DEFAULT_TERRAIN = new File("res/terrains/arenaTerrain/arenaTerrain.ter");
    private final File DEFAULT_PLAYER_MODEL = new File("res/models/poppetje.obj");
    private final File DEFAULT_PLAYER_TEXTURE = new File("res/textures/planks.png");
    private final File DEFAULT_OBJECT_MODEL = new File("res/models/bobcat.obj");
    private final File DEFAULT_OBJECT_TEXTURE = new File("res/textures/WhiteTexture.png");
    private final File DEFAULT_LAMP_MODEL = new File("res/models/lamp.obj");
    private final File DEFAULT_LAMP_TEXTURE = new File("res/textures/lampTexture.png");
    private final File DEFAULT_SUN_MODEL = new File("res/models/ball.obj");
    private final File DEFAULT_SUN_TEXTURE = new File("res/textures/WhiteTexture.png");
    private final File DEFAULT_BULLET_MODEL = new File("res/models/bullet.obj");
    private final File DEFAULT_BULLET_TEXTURE = new File("res/textures/WhiteTexture.png");
    //</editor-fold>

    /**
     * A reference to everything that should be rendered in 3D space, and
     * contains all logic for this 3D space.
     */
    private Scene scene;

    /**
     * A reference to everything that should be rendered in 2D space, and
     * contains all logic for this 2D space.
     */
    private UserInterface userInterface;

    /**
     * The renderer that is responsible for rendering of the scene and user
     * interface.
     */
    private MasterRenderer masterRenderer;

    /**
     * Gets the scene that is currently rendered to the display.
     *
     * @return Instance of scene that is currently rendered.
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Sets the scene that will be rendered to the display.
     *
     * @param scene Instance of the scene that will be rendered.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Gets the user interface that is currently rendered to the display.
     *
     * @return Instance of the user interface that is currently rendered.
     */
    public UserInterface getUserInterface() {
        return this.userInterface;
    }

    /**
     * Gets the user interface that is currently rendered to the display.
     *
     * @param userInterface Instance of the user interface that will be
     * rendered.
     */
    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    /**
     * Loads default terrain, player object, entities and lights to the scene.
     *
     * @throws IOException when files are not found.
     */
    public void loadDefaultScene() throws IOException {
        
        //<editor-fold defaultstate="collapsed" desc="Terrain">
        // Loads the default terrain.
        Terrain terrain = FileLoader.loadTerrain(DEFAULT_TERRAIN);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Entities">
        // Loads the default bullet textured model.
        RawModel bulletModel = OBJLoader.loadObjModel(DEFAULT_BULLET_MODEL);
        ModelTexture bulletTexture = new ModelTexture(Loader.loadTexture(DEFAULT_BULLET_TEXTURE));
        TexturedModel texturedBulletModel = new TexturedModel(bulletModel, bulletTexture);
        bulletTexture.setReflectivity(1);
        bulletTexture.setShineDamper(10);

        // Loads the default player entity.
        RawModel playerModel = OBJLoader.loadObjModel(DEFAULT_PLAYER_MODEL);
        ModelTexture playerTexture = new ModelTexture(Loader.loadTexture(DEFAULT_PLAYER_TEXTURE));
        TexturedModel texturedPlayerModel = new TexturedModel(playerModel, playerTexture);
        playerTexture.setReflectivity(0);
        playerTexture.setShineDamper(100);
        Player player = new Player(
                texturedPlayerModel,
                new Vector3f(15, 0, 15),
                new Vector3f(0, (float) Math.toRadians(45), 0),
                0.1f,
                texturedBulletModel);

        // Loads the default textured object model.
        RawModel objectModel = OBJLoader.loadObjModel(DEFAULT_OBJECT_MODEL);
        ModelTexture objectTexture = new ModelTexture(Loader.loadTexture(DEFAULT_OBJECT_TEXTURE));
        TexturedModel texturedObjectModel = new TexturedModel(objectModel, objectTexture);
        objectTexture.setReflectivity(1);
        objectTexture.setShineDamper(10);

        // Loads the default three different entities with this object model.
        Entity entity = new Entity(
                "target1",
                texturedObjectModel,
                new Vector3f(20, terrain.getHeightOfTerrain(60, 60), 20),
                new Vector3f(0, (float) Math.toRadians(-45), 0),
                0.5f);
        Entity entity1 = new Entity(
                "target2",
                texturedObjectModel,
                new Vector3f(55, terrain.getHeightOfTerrain(55, 60), 60),
                new Vector3f(0, (float) Math.toRadians(-60), 0),
                0.5f);
        Entity entity2 = new Entity(
                "target3",
                texturedObjectModel,
                new Vector3f(60, terrain.getHeightOfTerrain(60, 55), 55),
                new Vector3f(0, (float) Math.toRadians(-30), 0),
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
                "lamp1",
                texturedLampModel,
                new Vector3f(40, terrain.getHeightOfTerrain(40, 40), 40),
                new Vector3f(0, 0, 0),
                0.5f,
                new Vector3f(0, 6.6f, 0),
                new Vector3f(1, 1, 0));

        Lamp lamp1 = new Lamp(
                "lamp2",
                texturedLampModel,
                new Vector3f(55, terrain.getHeightOfTerrain(55, 55), 55),
                new Vector3f(0, 0, 0),
                0.5f,
                new Vector3f(0, 6.6f, 0),
                new Vector3f(1, 1, 0));

        // Loads the default textured sun model.
        RawModel sunModel = OBJLoader.loadObjModel(DEFAULT_SUN_MODEL).containsInvertedNormals();
        ModelTexture sunTexture = new ModelTexture(Loader.loadTexture(DEFAULT_SUN_TEXTURE));
        TexturedModel texturedSunModel = new TexturedModel(sunModel, sunTexture);
        sunTexture.setReflectivity(1);
        sunTexture.setShineDamper(10);

        // Loads the default sun entity
        Lamp sun = new Lamp("sun", texturedSunModel, new Vector3f(10, 30, 0), new Vector3f(0, 0, 0), 10, new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), new Vector3f(1f, 0f, 0));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Lights">
        // Creates a list for all the lights in the scene, and adds the
        // the lights of the previously created lamps to that list.
        List<Light> lights = new ArrayList<>();
        lights.add(lamp.getLight());
        lights.add(lamp1.getLight());
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Scene">
        // Creates the scene object with the player object, a new camera that
        // is relative to the player, the list of lights and the terrain.
        scene = new Scene(
                player,
                //new FreeCamera(new Vector3f(40,100,40), 90, 0,0),
                new ThirdPersonCamera(player),
                lights,
                terrain);

        // Adds the object entities to the scene.
        scene.addEntity(entity);
        scene.addEntity(entity1);
        scene.addEntity(entity2);
        scene.addTexturedModel(texturedBulletModel);

        // Adds the lamp entities to the scene.
        scene.addEntity(lamp);
        scene.addEntity(lamp1);

        scene.setSun(sun);
        //</editor-fold>

    }

    /**
     * Loads default user interface object.
     * @throws java.io.IOException when files are not found.
     */
    public void loadDefaultUserInterface() throws IOException {

        //<editor-fold defaultstate="collapsed" desc="User Interface">
        // Loads the default user interface.
        userInterface = new UserInterface();
        //</editor-fold>

    }

    /**
     * Creates the display, and sets up everything that is necessary for 
     * the game.
     */
    @Override
    public void setUp() {
        try {
            DisplayManager.createDisplay();
            Keyboard.create();
            Mouse.create();
            loadDefaultUserInterface();
            loadDefaultScene();

            this.masterRenderer = new MasterRenderer();
        } catch (IOException ex) {
            Logger.getLogger(BeamsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LWJGLException ex) {
            Logger.getLogger(BeamsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates state.
     */
    @Override
    public void update() {
        this.scene.update();
        this.userInterface.update();
    }

    /**
     * Handles user input.
     */
    @Override
    public void handleInput() {
        MouseInput.checkInputs();
        KeyboardInput.checkInputs();
    }

    /**
     * Renders everything that is necessary for the game.
     */
    @Override
    public void render() {
        this.masterRenderer.prepare();

        this.masterRenderer.render(this.scene);
        this.masterRenderer.render(this.userInterface);

        DisplayManager.updateDisplay();
    }

    /**
     * Cleans up everything.
     */
    @Override
    public void cleanUp() {
        TextMaster.cleanUp();
        Loader.cleanUp();
        this.masterRenderer.cleanUp();
        DisplayManager.closeDisplay();
    }

    /**
     * Private constructor
     */
    private BeamsClient() {
    }

    /**
     * The main program loop.
     *
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        beamsClient = new BeamsClient();
        beamsClient.start();
    }

    private static BeamsClient beamsClient;

    public static BeamsClient getInstance() {
        return beamsClient;
    }

}
