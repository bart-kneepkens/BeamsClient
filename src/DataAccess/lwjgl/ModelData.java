/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess.lwjgl;

/**
 * An instance of this class contains all initial data for a model, before being
 * bound to a VAO and VBO's.
 *
 * @author Blackened
 */
public class ModelData {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     * The positions of all vertices.
     */
    private float[] vertexPositions;

    /**
     * Coordinates of the texture;
     */
    private float[] textureCoords;

    /**
     * The normal vectors.
     */
    private float[] normals;

    /**
     * The indices of the model.
     */
    private int[] indices;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    /**
     * Getter for the vertex positions.
     *
     * @return All positions of the vertices.
     */
    public float[] getVertexPositions() {
        return vertexPositions;
    }

    /**
     * Getter for the indices.
     *
     * @return An integer array of all indices for this model.
     */
    public int[] getIndices() {
        return indices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getNormals() {
        return normals;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new instance of the ModelData class.
     *
     * @param vertexPositions The positions of all vertices.
     * @param textureCoords
     * @param normals
     * @param indices The indices for this model.
     */
    public ModelData(float[] vertexPositions, float[] textureCoords, float[] normals, int[] indices) {
        this.vertexPositions = vertexPositions;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
    }
//</editor-fold>

}
