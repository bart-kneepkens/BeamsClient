/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 * An instance of this class represents the raw data of a model. This includes
 * the VAO ID and the amount of vertices.
 *
 * @author Blackened
 */
public class RawModel {

    private int vaoID;
    private int vertexCount;

    /**
     * Creates a new instance of RawModel.
     *
     * @param vaoID The ID of the VAO, pointing towards the VAO in memory.
     * @param vertexCount The amount of vertices in this model.
     */
    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    /**
     * Getter for the VAO ID.
     *
     * @return The VAO ID of this instance of RawModel.
     */
    public int getVaoID() {
        return vaoID;
    }

    /**
     * Getter for the vertex count.
     *
     * @return The amount of vertices of this instance of RawModel.
     */
    public int getVertexCount() {
        return vertexCount;
    }

    public boolean doesEqual(RawModel other){
        return this.vaoID == other.getVaoID()
                    && this.vertexCount == other.getVertexCount();
    }
}
