/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terrain.lwjgl;

import models.RawModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.AttributeListPosition;
import static toolbox.AttributeListPosition.NORMAL_VECTORS;
import static toolbox.AttributeListPosition.TEXTURE_COORDS;
import static toolbox.AttributeListPosition.VERTEX_POSITIONS;
import renderEngine.Renderer;
import terrain.Terrain;
import textures.TerrainTexturePack;
import toolbox.Maths;

/**
 *
 * @author Blackened
 */
public class TerrainRenderer extends TerrainShader implements Renderer<Terrain>{

    private final Matrix4f projectionMatrix;

    public TerrainRenderer(Matrix4f projectionMatrix) {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        this.projectionMatrix = projectionMatrix;
        this.start();
        this.loadUniformMatrix("projectionMatrix", this.projectionMatrix);
        this.connectTextureUnits();
        this.stop();
    }

    @Override
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
        this.loadUniformFloat("shineDamper", 1);
        this.loadUniformFloat("reflectivity", 0);
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
        this.loadUniformMatrix("transformationMatrix", transformationMatrix);
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


}
