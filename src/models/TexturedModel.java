/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import textures.ModelTexture;

/**
 * An instance of this class contains a raw model and a model texture.
 * @author Blackened
 */
public class TexturedModel {
    
    /**
     * The raw model.
     */
    private RawModel rawModel;
    
    /**
     * The model texture for the raw model in this instance.
     */
    private ModelTexture modelTexture;

    /**
     * Creates an instance of the TexturedModel class.
     * @param rawModel The raw model.
     * @param modelTexture The model texture for this model.
     */
    public TexturedModel(RawModel rawModel, ModelTexture modelTexture) {
        this.rawModel = rawModel;
        this.modelTexture = modelTexture;
    }

    /**
     * Getter for the raw model.
     * @return The raw model in this instance.
     */
    public RawModel getRawModel() {
        return rawModel;
    }

    /**
     * Getter for the model texture.
     * @return The model texture in this instance.
     */
    public ModelTexture getModelTexture() {
        return modelTexture;
    }
    
}
