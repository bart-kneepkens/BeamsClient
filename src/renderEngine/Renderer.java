/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import static renderEngine.AttributeListName.*;
import shaders.StaticShader;
import toolbox.Maths;

/**
 * An instance of this class will render models.
 * @author Blackened
 */
public class Renderer {
    
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    
    private Matrix4f projectionMatrix;

    /**
     * Creates a new instance of the Renderer class.
     * @param shader The shader program into which the projection matrix has 
     * to be loaded.
     */
    public Renderer(StaticShader shader) {
        this.createProjectionMatrix();
        shader.start();
        shader.loadUniformMatrix("projectionMatrix", projectionMatrix);
        shader.stop();
    }
    
    
    
    /**
     * Clears the display and sets its background colour.
     */
    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1, 0, 0, 1);
    }
    
    /**
     * Renders an instance of RawModel to the screen.
     * @param entity The entity to be rendered.
     * @param shader The shader that is being used while rendering.
     */
    public void render(Entity entity, StaticShader shader){
        TexturedModel texturedModel = entity.getModel();
        RawModel rawModel = texturedModel.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        enableAttributeArray(VERTEX_POSITIONS);
        enableAttributeArray(TEXTURE_COORDS);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        shader.loadUniformMatrix("transformationMatrix", transformationMatrix);
        
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getModelTexture().getTextureID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        disableAttributeArray(VERTEX_POSITIONS);
        disableAttributeArray(TEXTURE_COORDS);
        GL30.glBindVertexArray(0);
    }
    
    /**
     * Enables the attribute list specified by the name.
     * @param model The model for which to enable the attribute list.
     * @param attribName The name of the attributeList.
     */
    private void enableAttributeArray(AttributeListName attribName){
        GL20.glEnableVertexAttribArray(attribName.getNumVal());
    }
    
    /**
     * Disables an enabled attribute list
     */
    private void disableAttributeArray(AttributeListName attribName){
        GL20.glDisableVertexAttribArray(attribName.getNumVal());
    }
    
    /**
     * Creates a new projection matrix in accordance with the FOV, FAR_PLANE,
     * NEAR_PLANE and display size.
     */
    private void createProjectionMatrix(){
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
