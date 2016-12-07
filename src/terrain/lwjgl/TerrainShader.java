/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terrain.lwjgl;

import entity.Light;
import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.ShaderProgram;
import static toolbox.AttributeListPosition.NORMAL_VECTORS;
import static toolbox.AttributeListPosition.TEXTURE_COORDS;
import static toolbox.AttributeListPosition.VERTEX_POSITIONS;

/**
 *
 * @author Blackened
 */
public class TerrainShader extends ShaderProgram{
    
    private static final int MAX_LIGHTS = 4;
    
    private int location_lightPosition[];
    private int location_lightColour[];
    
    /**
     * The locations of the shader files.
     */
    private static final String VERTEX_FILE = "src/terrain/lwjgl/vertexShader";
    private static final String FRAGMENT_FILE = "src/terrain/lwjgl/fragmentShader";
    
    

    /**
     * Creates a new instance of the static shader class.
     */
    protected TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    /**
     * Binds all attributes to the variable names in the shaders.
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS.getNumVal(), "position");
        super.bindAttribute(TEXTURE_COORDS.getNumVal(), "textureCoords");
        super.bindAttribute(NORMAL_VECTORS.getNumVal(), "normal");
    }

    /**
     * Gets all uniform locations and stores them in the uniformLocations map.
     */
    @Override
    protected void getAllUniformLocations() {
        uniformLocations.put("transformationMatrix", super.getUniformLocation("transformationMatrix"));
        uniformLocations.put("projectionMatrix", super.getUniformLocation("projectionMatrix"));
        uniformLocations.put("viewMatrix", super.getUniformLocation("viewMatrix"));
        uniformLocations.put("shineDamper", super.getUniformLocation("shineDamper"));
        uniformLocations.put("reflectivity", super.getUniformLocation("reflectivity"));
        uniformLocations.put("backgroundTexture", super.getUniformLocation("backgroundTexture"));
        uniformLocations.put("rTexture", super.getUniformLocation("rTexture"));
        uniformLocations.put("gTexture", super.getUniformLocation("gTexture"));
        uniformLocations.put("bTexture", super.getUniformLocation("bTexture"));
        uniformLocations.put("blendMap", super.getUniformLocation("blendMap"));

        this.getLightUniformLocations();
    }

    public void getLightUniformLocations() {
        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];

        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
        }
    }
    
    protected final void connectTextureUnits(){
        super.loadInt(uniformLocations.get("backgroundTexture"), 0);
        super.loadInt(uniformLocations.get("rTexture"), 1);
        super.loadInt(uniformLocations.get("gTexture"), 2);
        super.loadInt(uniformLocations.get("bTexture"), 3);
        super.loadInt(uniformLocations.get("blendMap"), 4);
        
    }
    
    protected void loadUniformFloat(String uniformName, float value){
        super.loadFloat(uniformLocations.get(uniformName), value);
    }
    
    /**
     * Loads a matrix into a uniform variable. Throws an exception if uniform
     * variable is not found.
     * @param uniformName The name of the uniform variable.
     * @param matrix The matrix to be loaded into the uniform variable.
     */
    public final void loadUniformMatrix(String uniformName, Matrix4f matrix){
        super.loadMatrix(uniformLocations.get(uniformName), matrix);
    }
    
    /**
     * Loads the light position and colour to the uniform variable.
     * @param light The light to be loaded.
     */
    public void loadLights(List<Light> lights){
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
                super.loadVector(location_lightColour[i], lights.get(i).getColour());
            } else {
                super.loadVector(location_lightPosition[i], new Vector3f(0,0,0));
                super.loadVector(location_lightColour[i], new Vector3f(0,0,0));
            }
        }
    }
    
    
    
    
}
