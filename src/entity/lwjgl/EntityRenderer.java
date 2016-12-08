/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.lwjgl;

import entity.Entity;
import entity.texture.TexturedModel;
import java.util.List;
import java.util.Map.Entry;
import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.Renderer;
import static toolbox.AttributeListPosition.*;
import toolbox.Maths;

/**
 * An instance of this class can render entities.
 *
 * @author Blackened
 */
public class EntityRenderer extends StaticShader implements Renderer<Entity> {

    /**
     * The projection matrix for all entities that will be rendered with this
     * instance.
     */
    private final Matrix4f projectionMatrix;

    /**
     * Creates a new instance of the Renderer class. Loads the projection matrix
     * to the vertex shader.
     *
     * @param projectionMatrix The projection matrix that will be passed on to
     * the shader when rendering an entity.
     */
    public EntityRenderer(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        this.start();
        this.loadUniformMatrix("projectionMatrix", this.projectionMatrix);
        this.stop();
    }

    public void render(Entry<TexturedModel, List<Entity>> entityBatch) {
        this.prepareTexturedModel(entityBatch.getKey());

        entityBatch.getValue().stream().forEach(x -> {
            this.loadModelMatrix(x);
            GL11.glDrawElements(GL11.GL_TRIANGLES, x.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        });

        this.unbindTexturedModel();
    }

    /**
     * Renders an entity to the screen.
     *
     * @param entity The entity to be rendered.
     */
    @Override
    public void render(Entity entity) {
        if (entity.containsInvertedNormals()) {
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
        this.prepareTexturedModel(entity.getModel());
        this.loadModelMatrix(entity);

        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        unbindTexturedModel();

        if (entity.containsInvertedNormals()) {
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glEnableVertexAttribArray(TEXTURE_COORDS);
        GL20.glEnableVertexAttribArray(NORMAL_VECTORS);
        this.loadUniformFloat("shineDamper", model.getModelTexture().getShineDamper());
        this.loadUniformFloat("reflectivity", model.getModelTexture().getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModelTexture().getTextureID());
    }

    private void loadModelMatrix(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        this.loadUniformMatrix("transformationMatrix", transformationMatrix);
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glDisableVertexAttribArray(TEXTURE_COORDS);
        GL20.glDisableVertexAttribArray(NORMAL_VECTORS);
        GL30.glBindVertexArray(0);
    }

}
