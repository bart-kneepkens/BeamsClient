/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.texture;

/**
 *
 * @author Blackened
 */
public class ModelTexture {
    
    /**
     * The ID of the texture.
     */
    private final int textureID;
    
    private float shineDamper = 1;
    private float reflectivity = 0;

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    /**
     * Creates a new instance of the ModelTexture class.
     * @param textureID The ID of the texture.
     */
    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    /**
     * Getter for the textures ID.
     * @return The ID of the texture.
     */
    public int getTextureID() {
        return textureID;
    }
    
    public boolean doesEqual(ModelTexture other){
        return this.textureID == other.getTextureID()
                && Math.abs(this.shineDamper - other.shineDamper) < 0.000001f
                && Math.abs(this.reflectivity - other.reflectivity) < 0.000001f;
    }
    
    
    
}
