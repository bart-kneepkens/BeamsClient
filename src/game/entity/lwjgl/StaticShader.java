/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity.lwjgl;

import game.entity.Light;
import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.ShaderProgram;
import static toolbox.AttributeListPosition.*;

/**
 * An extension of ShaderProgram, with predefined file-paths to a vertex- and
 * fragment-shader.
 *
 * @author Blackened
 */
public class StaticShader extends ShaderProgram {

    //<editor-fold defaultstate="collapsed" desc="Static Properties">
    private static final int MAX_LIGHTS = 4;

    /**
     * The locations of the shader files.
     */
    private static final String VERTEX_FILE = "src/Game/entity/lwjgl/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/Game/entity/lwjgl/fragmentShader.glsl";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new instance of the static shader class.
     */
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void getLightUniformLocations() {
        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];

        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
    }

    public void loadUniformFloat(String uniformName, float value) {
        super.loadFloat(super.getUniformLocations().get(uniformName), value);
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

    /**
     * Loads the light position and colour to the uniform variable.
     *
     * @param lights
     */
    public void loadLights(List<Light> lights) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
                super.loadVector(location_lightColour[i], lights.get(i).getColour());
                super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
            } else {
                super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
                super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
                super.loadVector(location_attenuation[i], new Vector3f(4, 0, 0));
            }
        }
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
        super.bindAttribute(NORMAL_VECTORS, "normal");
    }

    /**
     * Gets all uniform locations and stores them in the uniformLocations map.
     */
    @Override
    protected void getAllUniformLocations() {
        super.getUniformLocations().put("transformationMatrix", super.getUniformLocation("transformationMatrix"));
        super.getUniformLocations().put("projectionMatrix", super.getUniformLocation("projectionMatrix"));
        super.getUniformLocations().put("viewMatrix", super.getUniformLocation("viewMatrix"));
        super.getUniformLocations().put("shineDamper", super.getUniformLocation("shineDamper"));
        super.getUniformLocations().put("reflectivity", super.getUniformLocation("reflectivity"));

        this.getLightUniformLocations();
    }
//</editor-fold>

}
