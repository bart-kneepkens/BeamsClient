/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

import GUI.GUI;
import terrain.lwjgl.TerrainRenderer;
import GUI.lwjgl.GUIRenderer;
import Game.Scene;
import entity.Entity;
import entity.lwjgl.EntityRenderer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import terrain.Terrain;
import toolbox.Maths;

/**
 *
 * @author Blackened
 */
public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private final GUIRenderer guiRenderer;
    private EntityRenderer entityRenderer;
    private final TerrainRenderer terrainRenderer;

    Matrix4f viewMatrix;

    public MasterRenderer() {
        Matrix4f projectionMatrix = this.createProjectionMatrix();
        this.guiRenderer = new GUIRenderer();
        this.terrainRenderer = new TerrainRenderer(projectionMatrix);
        this.entityRenderer = new EntityRenderer(projectionMatrix);
    }

    /**
     * Clears the display and sets its background colour.
     *
     */
    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0, 0, 0, 1);
    }

    /**
     * Prepares for 3d rendering by creating the view matrix
     *
     * @param scene
     */
    public void startTerrainRendering(Scene scene) {
        this.viewMatrix = Maths.createViewMatrix(scene.getCamera());
        this.terrainRenderer.start();
        this.terrainRenderer.loadLights(scene.getLights());
        this.terrainRenderer.loadUniformMatrix("viewMatrix", viewMatrix);
    }
    
    public void startEntityRendering(Scene scene){
        this.entityRenderer.start();
        this.entityRenderer.loadLights(scene.getLights());
        this.entityRenderer.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    public void stopTerrainRendering() {
        this.terrainRenderer.stop();
    }
    
    public void stopEntityRendering(){
        this.entityRenderer.stop();
    }

    public void render(GUI gui) {
        this.guiRenderer.start();
        gui.getGuiElements().keySet().forEach(x -> {
            this.guiRenderer.render(x);
        });
        this.guiRenderer.stop();
    }

    public void render(Terrain terrain) {
        if (terrain != null) {
            this.terrainRenderer.render(terrain);
        }
    }

    public void render(Entity entity) {
        if (entity != null) {
            this.entityRenderer.render(entity);
        }
    }
    
    public void render(Scene scene){
        this.startTerrainRendering(scene);
        this.render(scene.getTerrain());
        this.stopTerrainRendering();
        
        this.startEntityRendering(scene);
        this.render(scene.getPlayer());
        scene.getEntities().forEach(x -> this.render(x));
        this.stopEntityRendering();
    }

    public void cleanUp() {
        this.guiRenderer.cleanUp();
    }

    /**
     * Creates a new projection matrix in accordance with the FOV, FAR_PLANE,
     * NEAR_PLANE and display size.
     */
    private Matrix4f createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;

        return projectionMatrix;
    }

}
