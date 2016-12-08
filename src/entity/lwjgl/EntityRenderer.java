/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.lwjgl;

import entity.Entity;
import entity.texture.TexturedModel;
import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.Renderer;
import toolbox.AttributeListPosition;
import static toolbox.AttributeListPosition.*;
import toolbox.Maths;

/**
 *
 * @author Blackened
 */
public class EntityRenderer extends StaticShader implements Renderer<Entity>{
   
    
    private final Matrix4f projectionMatrix;

    /**
     * Creates a new instance of the Renderer class.
     *
     * @param projectionMatrix
     */
    public EntityRenderer(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        this.start();
        this.loadUniformMatrix("projectionMatrix", this.projectionMatrix);
        this.stop();
    }

    /**
     * Clears the display and sets its background colour.
     */
    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1, 0, 0, 1);
    }

    /**
     * Renders an instance of RawModel to the screen.
     *
     * @param entity The entity to be rendered.
     */
    @Override
    public void render(Entity entity) {
        if(entity.containsInvertedNormals()){
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
        this.prepareTexturedModel(entity.getModel());
        this.loadModelMatrix(entity);
        
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        
        unbindTexturedModel();
        
        if(entity.containsInvertedNormals()){
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
    }
    
    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        enableAttributeArray(VERTEX_POSITIONS);
        enableAttributeArray(TEXTURE_COORDS);
        enableAttributeArray(NORMAL_VECTORS);
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
