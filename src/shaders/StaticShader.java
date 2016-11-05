/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shaders;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.util.vector.Matrix4f;
import static renderEngine.AttributeListName.*;

/**
 * An extension of ShaderProgram, with predefined file-paths to a vertex- and 
 * fragment-shader.
 * @author Blackened
 */
public class StaticShader extends ShaderProgram{
    
    /**
     * The locations of the shader files.
     */
    private static final String VERTEX_FILE = "src/shaders/vertexShader";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader";
    
    

    /**
     * Creates a new instance of the static shader class.
     */
    public StaticShader() {
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
        uniformLocations.put("projectionMatrix", super.getUniformLocation("projectionMatrix"));
        uniformLocations.put("viewMatrix", super.getUniformLocation("viewMatrix"));
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
    
    
    
}
