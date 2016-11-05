/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textures;

/**
 *
 * @author Blackened
 */
public class ModelTexture {
    
    /**
     * The ID of the texture.
     */
    private int textureID;

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
    
    
    
}
