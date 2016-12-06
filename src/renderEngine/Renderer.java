/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

import java.util.List;
import models.RawModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import static renderEngine.AttributeListPosition.NORMAL_VECTORS;
import static renderEngine.AttributeListPosition.TEXTURE_COORDS;
import static renderEngine.AttributeListPosition.VERTEX_POSITIONS;
import shaders.TerrainShader;
import terrains.Terrain;
import textures.TerrainTexturePack;
import toolbox.Maths;

/**
 *
 * @author Blackened
 */
public class Renderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;

    private TerrainShader shader;

    public Renderer(TerrainShader shader) {
        this.shader = shader;
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        this.createProjectionMatrix();
        this.shader.start();
        this.shader.loadUniformMatrix("projectionMatrix", projectionMatrix);
        this.shader.connectTextureUnits();
        this.shader.stop();
    }

    public void render(Terrain terrain) {
        prepareTerrain(terrain);
        loadModelMatrix(terrain);

        GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        unbindTexturedModel();

    }

    private void prepareTerrain(Terrain terrain) {
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        enableAttributeArray(VERTEX_POSITIONS);
        enableAttributeArray(TEXTURE_COORDS);
        enableAttributeArray(NORMAL_VECTORS);
        bindTextures(terrain);
        this.shader.loadUniformFloat("shineDamper", 1);
        this.shader.loadUniformFloat("reflectivity", 0);
    }

    private void bindTextures(Terrain terrain) {
        TerrainTexturePack texturePack = terrain.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), terrain.getRotation(), 1);
        shader.loadUniformMatrix("transformationMatrix", transformationMatrix);
    }

    private void unbindTexturedModel() {
        disableAttributeArray(VERTEX_POSITIONS);
        disableAttributeArray(TEXTURE_COORDS);
        disableAttributeArray(NORMAL_VECTORS);
        GL30.glBindVertexArray(0);
    }

    /**
     * Enables the attribute list specified by the name.
     *
     * @param model The model for which to enable the attribute list.
     * @param attribName The name of the attributeList.
     */
    private void enableAttributeArray(AttributeListPosition attribName) {
        GL20.glEnableVertexAttribArray(attribName.getNumVal());
    }

    /**
     * Disables an enabled attribute list
     */
    private void disableAttributeArray(AttributeListPosition attribName) {
        GL20.glDisableVertexAttribArray(attribName.getNumVal());
    }

    /**
     * Creates a new projection matrix in accordance with the FOV, FAR_PLANE,
     * NEAR_PLANE and display size.
     */
    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

}
