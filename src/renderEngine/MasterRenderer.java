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
 * An instance of this class distributes objects that need to be rendered over
 * other (more specific) rendering instances.
 *
 * @author Blackened
 */
public class MasterRenderer {

    /**
     * The field of view with which the objects will be rendered.
     */
    private static final float FOV = 70;

    /**
     * The near plane with which the objects will be rendered.
     */
    private static final float NEAR_PLANE = 0.1f;

    /**
     * The far plane with which the objects will be rendered.
     */
    private static final float FAR_PLANE = 1000;

    /**
     * A renderer and shader that is specified in rendering graphical user
     * interface elements.
     */
    private final GUIRenderer guiRenderer;

    /**
     * A renderer and shader that is specified in rendering 3D entities in 3D
     * space.
     */
    private final EntityRenderer entityRenderer;

    /**
     * A renderer and shader that is specified in rendering terrains in 3D
     * space.
     */
    private final TerrainRenderer terrainRenderer;

    /**
     * This matrix determines which objects can be seen with the camera object,
     * and is used for all 3D rendering.
     */
    private Matrix4f viewMatrix;

    /**
     * Creates a new instance of the MasterRenderer class.
     */
    public MasterRenderer() {
        Matrix4f projectionMatrix = this.createProjectionMatrix();
        this.guiRenderer = new GUIRenderer();
        this.terrainRenderer = new TerrainRenderer(projectionMatrix);
        this.entityRenderer = new EntityRenderer(projectionMatrix);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
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
     * Renders a scene to the display with respect to the view of the camera
     * that is in the scene.
     *
     * @param scene The scene to be rendered to the display.
     */
    public void render(Scene scene) {
        this.createViewMatrix(scene);

        this.startTerrainRendering(scene);
        this.render(scene.getTerrain());
        this.stopTerrainRendering();

        this.startEntityRendering(scene);
        this.render(scene.getPlayer());
        scene.getEntities().entrySet().forEach(x -> entityRenderer.render(x));
        this.stopEntityRendering();
    }

    /**
     * Renders a graphical user interface to the display.
     *
     * @param gui The user interface to be rendered to the display.
     */
    public void render(GUI gui) {
        this.guiRenderer.start();
        gui.getGuiElements().keySet().forEach(x -> {
            this.guiRenderer.render(x);
        });
        this.guiRenderer.stop();
    }

    /**
     * Cleans up the shaders.
     */
    public void cleanUp() {
        this.guiRenderer.cleanUp();
        this.entityRenderer.cleanUp();
        this.terrainRenderer.cleanUp();
    }

    /**
     * Prepares for 3D rendering by creating the view matrix
     *
     * @param scene
     */
    private void createViewMatrix(Scene scene) {
        this.viewMatrix = Maths.createViewMatrix(scene.getCamera());
    }

    /**
     * Does all the necessary preparations needed to render the terrain of a
     * scene.
     *
     * @param scene The scene of which the terrain will be rendered.
     */
    private void startTerrainRendering(Scene scene) {
        this.terrainRenderer.start();
        this.terrainRenderer.loadLights(scene.getLights());
        this.terrainRenderer.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    /**
     * Does all the necessary preparations needed to render the entities of a
     * scene.
     *
     * @param scene The scene of which the entities will be rendered.
     */
    private void startEntityRendering(Scene scene) {
        this.entityRenderer.start();
        this.entityRenderer.loadLights(scene.getLights());
        this.entityRenderer.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    /**
     * Cleans up everything that is no longer needed after the terrain of a
     * scene has been rendered.
     */
    private void stopTerrainRendering() {
        this.terrainRenderer.stop();
    }

    /**
     * Cleans up everything that is no longer needed after the entities of a
     * scene have been rendered.
     */
    private void stopEntityRendering() {
        this.entityRenderer.stop();
    }

    /**
     * If the terrain is not null, this terrain will be rendered by the
     * terrainRenderer.
     *
     * @param terrain Terrain to be rendered. Can be null.
     */
    private void render(Terrain terrain) {
        if (terrain != null) {
            this.terrainRenderer.render(terrain);
        }
    }

    /**
     * If the entity is not null, this entity will be rendered by the
     * entityRenderer.
     *
     * @param entity Entity to be rendered. Can be null.
     */
    private void render(Entity entity) {
        if (entity != null) {
            this.entityRenderer.render(entity);
        }
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
