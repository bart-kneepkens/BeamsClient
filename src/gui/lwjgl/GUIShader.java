/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.lwjgl;

import org.lwjgl.util.vector.Matrix4f;
import static toolbox.AttributeListPosition.TEXTURE_COORDS;
import static toolbox.AttributeListPosition.VERTEX_POSITIONS;
import renderEngine.ShaderProgram;

/**
 *
 * @author Blackened
 */
public class GUIShader extends ShaderProgram {

    //<editor-fold defaultstate="collapsed" desc="Static Properties">
    /**
     * The locations of the shader files.
     */
    private static final String VERTEX_FILE = "src/GUI/lwjgl/vertexShaderGUI.glsl";
    private static final String FRAGMENT_FILE = "src/GUI/lwjgl/fragmentShaderGUI.glsl";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public GUIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Loads a matrix into a uniform variable. Throws an exception if uniform
     * variable is not found.
     *
     * @param uniformName The name of the uniform variable.
     * @param matrix The matrix to be loaded into the uniform variable.
     */
    public void loadUniformMatrix(String uniformName, Matrix4f matrix) {
        super.loadMatrix(super.getUniformLocations().get(uniformName), matrix);
    }

    public void loadFloat(String uniformName, float value) {
        super.loadFloat(super.getUniformLocations().get(uniformName), value);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Protected Methods">
    /**
     * Binds all attributes to the variable names in the shaders.
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS, "position");
        super.bindAttribute(TEXTURE_COORDS, "textureCoords");
    }

    /**
     * Gets all uniform locations and stores them in the uniformLocations map.
     */
    @Override
    protected void getAllUniformLocations() {
        super.getUniformLocations().put("transformationMatrix", super.getUniformLocation("transformationMatrix"));
    }
//</editor-fold>

}
