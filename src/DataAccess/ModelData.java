/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;


/**
 * An instance of this class contains all initial data for a model,
 * before being bound to a VAO and VBO's.
 * @author Blackened
 */
public class ModelData {
    
    /**
     * The positions of all vertices.
     */
    private float[] vertexPositions;
    
    /**
     * Coordinates of the texture;
     */
    private float[] textureCoords;
    
    /**
     * The indices of the model.
     */
    private int[] indices;

    /**
     * Creates a new instance of the ModelData class.
     * @param vertexPositions The positions of all vertices.
     * @param indices The indices for this model.
     */
    public ModelData(float[] vertexPositions, float[] textureCoords, int[] indices) {
        this.vertexPositions = vertexPositions;
        this.textureCoords = textureCoords;
        this.indices = indices;
    }

    /**
     * Getter for the vertex positions.
     * @return All positions of the vertices.
     */
    public float[] getVertexPositions() {
        return vertexPositions;
    }

    /**
     * Getter for the indices.
     * @return An integer array of all indices for this model.
     */
    public int[] getIndices() {
        return indices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }
    
    
    
    
    
}
