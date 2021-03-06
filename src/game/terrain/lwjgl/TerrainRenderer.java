/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.terrain.lwjgl;

import game.terrain.texture.TerrainTexturePack;
import game.entity.models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import static toolbox.AttributeListPosition.*;
import renderEngine.Renderer;
import game.terrain.Terrain;
import toolbox.Maths;

/**
 *
 * @author Blackened
 */
public class TerrainRenderer extends TerrainShader implements Renderer<Terrain> {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     *
     */
    private final Matrix4f projectionMatrix;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     *
     * @param projectionMatrix
     */
    public TerrainRenderer(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        this.start();
        this.loadUniformMatrix("projectionMatrix", this.projectionMatrix);
        this.connectTextureUnits();
        this.stop();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     *
     * @param terrain
     */
    @Override
    public void render(Terrain terrain) {
        prepareTerrain(terrain);
        loadModelMatrix(terrain);

        GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        unbindTexturedModel();

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     *
     * @param terrain
     */
    private void prepareTerrain(Terrain terrain) {
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glEnableVertexAttribArray(TEXTURE_COORDS);
        GL20.glEnableVertexAttribArray(NORMAL_VECTORS);
        bindTextures(terrain);
    }

    /**
     *
     * @param terrain
     */
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

    /**
     *
     * @param terrain
     */
    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), terrain.getRotation(), 1);
        this.loadUniformMatrix("transformationMatrix", transformationMatrix);
    }

    /**
     *
     */
    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glDisableVertexAttribArray(TEXTURE_COORDS);
        GL20.glDisableVertexAttribArray(NORMAL_VECTORS);
        GL30.glBindVertexArray(0);
    }
//</editor-fold>

}
