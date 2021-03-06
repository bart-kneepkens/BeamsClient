/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.skybox.lwjgl;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.ShaderProgram;
import static toolbox.AttributeListPosition.TEXTURE_COORDS;
import static toolbox.AttributeListPosition.VERTEX_POSITIONS;

/**
 *
 * @author Blackened
 */
public class SkyboxShader extends ShaderProgram {

    /**
     * The location of the vertex shader file.
     */
    private static final String VERTEX_FILE = "src/game/skybox/lwjgl/vertexShader.glsl";

    /**
     * The location of the fragment shader file.
     */
    private static final String FRAGMENT_FILE = "src/game/skybox/lwjgl/fragmentShader.glsl";
    
    private static final float ROTATE_SPEED = 0.25f;
    
    private float rotation = 0;

    /**
     * Creates a new instance of the static shader class.
     */
    protected SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    /**
     * Gets all uniform locations and stores them in the uniformLocations map.
     */
    @Override
    protected void getAllUniformLocations() {
        super.getUniformLocations().put("projectionMatrix", super.getUniformLocation("projectionMatrix"));
        super.getUniformLocations().put("viewMatrix", super.getUniformLocation("viewMatrix"));
        super.getUniformLocations().put("cubeMap", super.getUniformLocation("cubeMap"));
    }

    /**
     * Binds all attributes to the variable names in the shaders.
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS, "position");
        super.bindAttribute(TEXTURE_COORDS, "textureCoords");
    }
    
        /**
     * Loads a matrix into a uniform variable. Throws an exception if uniform
     * variable is not found.
     *
     * @param uniformName The name of the uniform variable.
     * @param matrix The matrix to be loaded into the uniform variable.
     */
    public final void loadUniformMatrix(String uniformName, Matrix4f matrix) {
        super.loadMatrix(super.getUniformLocations().get(uniformName), matrix);
    }
    
    public final void loadViewMatrix(Matrix4f matrix){
        rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
        Matrix4f copy = new Matrix4f();
        Matrix4f.load(matrix, copy);
        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(1,1,0), copy, copy);
        
        this.loadUniformMatrix("viewMatrix", copy);
    }

}
