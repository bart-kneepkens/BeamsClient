/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity.models;

/**
 * An instance of this class represents the raw data of a model. This includes
 * the VAO ID and the amount of vertices.
 *
 * @author Blackened
 */
public class RawModel {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private int vaoID;

    private int vertexCount;

    private boolean containsInvertedNormals = false;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public boolean doesContainInvertedNormals() {
        return containsInvertedNormals;
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
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
     * Creates a new instance of RawModel.
     *
     * @param vaoID The ID of the VAO, pointing towards the VAO in memory.
     * @param vertexCount The amount of vertices in this model.
     * @param containsInvertedNormals
     */
    public RawModel(int vaoID, int vertexCount, boolean containsInvertedNormals) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.containsInvertedNormals = containsInvertedNormals;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public RawModel containsInvertedNormals() {
        return new RawModel(vaoID, vertexCount, true);
    }

    public boolean doesEqual(RawModel other) {
        return this.vaoID == other.getVaoID()
                && this.vertexCount == other.getVertexCount()
                && this.containsInvertedNormals == other.doesContainInvertedNormals();
    }
//</editor-fold>

}
