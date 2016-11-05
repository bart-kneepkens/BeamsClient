/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shaders.GUI;

import org.lwjgl.util.vector.Matrix4f;
import static renderEngine.AttributeListName.TEXTURE_COORDS;
import static renderEngine.AttributeListName.VERTEX_POSITIONS;
import shaders.ShaderProgram;

/**
 *
 * @author Blackened
 */
public class GUIShader extends ShaderProgram{
    
    
    /**
     * The locations of the shader files.
     */
    private static final String VERTEX_FILE = "src/shaders/GUI/vertexShaderGUI";
    private static final String FRAGMENT_FILE = "src/shaders/GUI/fragmentShaderGUI";

    public GUIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    


    /**
     * Binds all attributes to the variable names in the shaders.
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS.getNumVal(), "position");
        super.bindAttribute(TEXTURE_COORDS.getNumVal(), "textureCoords");
    }

    /**
     * Gets all uniform locations and stores them in the uniformLocations map.
     */
    @Override
    protected void getAllUniformLocations() {
        uniformLocations.put("transformationMatrix", super.getUniformLocation("transformationMatrix"));
    }
    
    /**
     * Loads a matrix into a uniform variable. Throws an exception if uniform
     * variable is not found.
     * @param uniformName The name of the uniform variable.
     * @param matrix The matrix to be loaded into the uniform variable.
     */
    public void loadUniformMatrix(String uniformName, Matrix4f matrix){
        super.loadMatrix(uniformLocations.get(uniformName), matrix);
    }
    
    public void loadFloat(String uniformName, float value){
        super.loadFloat(uniformLocations.get(uniformName), value);
    }
    
    
    
}
