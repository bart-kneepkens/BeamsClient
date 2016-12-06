/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.lib.GUIElement;
import GUI.objects.Button;
import GUI.objects.InvisibleListener;
import beamsClient.BeamsClient;
import entities.Camera;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class GUIManager {

    private final List<GUIElement> guiElements;
    private final Camera camera;
    private final Loader loader;
    private Terrain terrain;
    private TerrainTexturePack texturePack;
    private TerrainTexture blendMap;
    private BufferedImage heightMap;

    public GUIManager(Camera camera, Loader loader) {

        this.guiElements = new ArrayList<>();
        this.camera = camera;
        this.loader = loader;

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("default/cobble"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("default/mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("default/grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("default/spaghettiTexture"));
        this.texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        this.blendMap = new TerrainTexture(loader.loadTexture("default/blendMap"));

        this.heightMap = this.loadHeightMap(new File("res/textures/default/heightMap.png"));

        //<editor-fold defaultstate="collapsed" desc="Buttons">
        // Button 1
        Button newButton = new Button(75, 75, new Vector2f(Display.getWidth() / 2 - 225, 50));
        newButton.load();
        newButton.onClick(x -> this.button1_Click(x));
        newButton.loadMainTexture("buttons/exit_main");
        newButton.loadHoverTexture("buttons/exit_hover");
        newButton.loadClickTexture("buttons/exit_click");
        newButton.subscribe(MouseInput.getMouseSubject());
        this.guiElements.add(newButton.getGUIElement());

        Button newButton2 = new Button(75, 75, new Vector2f(Display.getWidth() / 2 - 150, 50));
        newButton2.load();
        newButton2.onClick(x -> this.button2_Click(x));
        newButton2.loadMainTexture("buttons/camera_main");
        newButton2.loadHoverTexture("buttons/camera_hover");
        newButton2.loadClickTexture("buttons/camera_click");
        newButton2.subscribe(MouseInput.getMouseSubject());
        this.guiElements.add(newButton2.getGUIElement());

        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Panel">
        InvisibleListener listener1 = new InvisibleListener(Display.getWidth() - 2, Display.getHeight() - 2, new Vector2f(Display.getWidth() / 2, (Display.getHeight()  / 2)), new Vector3f(0, 0, 0));
        listener1.onScroll(x -> this.listener1_Scroll(x));
        listener1.onPress(x -> listener1_Press(x));
        listener1.subscribe(MouseInput.getMouseSubject());

        //</editor-fold>
        this.updateTerrain();
    }

    public List<GUIElement> getAllGUIElements() {
        return this.guiElements;
    }

    private void button1_Click(Event event) {
        BeamsClient.keepRunning = false;
    }

    private void button2_Click(Event event) {
        this.camera.reset();
    }

    private void listener1_Scroll(Event event) {
        if (event.getMouseState().getScroll() < 0) {
            this.camera.increasePosition(new Vector3f(0, 0, -(this.camera.getPosition().getZ() / 1.0001f) * DisplayManager.getFrameTimeSeconds()));
        } else if (event.getMouseState().getScroll() > 0) {
            this.camera.increasePosition(new Vector3f(0, 0, (this.camera.getPosition().getZ() / 1.0001f) * DisplayManager.getFrameTimeSeconds()));
        }
    }

    private void listener1_Press(Event event) {
        if (event.getMouseState().isButton1Down()) {
            this.camera.turnHorizontally((event.getMouseState().getPressOrigin().x - event.getMouseState().getX()) / 500);
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }

    private void updateTerrain() {
        this.terrain = new Terrain(-1f, -0.5f, loader, this.texturePack, this.blendMap, this.heightMap);
    }

    private BufferedImage loadHeightMap(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);

        } catch (IOException ex) {
            Logger.getLogger(Terrain.class.getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }

}
