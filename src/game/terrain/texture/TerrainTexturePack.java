/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.terrain.texture;

/**
 *
 * @author Blackened
 */
public class TerrainTexturePack {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     *
     */
    private TerrainTexture backgroundTexture;

    /**
     *
     */
    private TerrainTexture rTexture;

    /**
     *
     */
    private TerrainTexture gTexture;

    /**
     *
     */
    private TerrainTexture bTexture;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    /**
     *
     * @return
     */
    public TerrainTexture getBackgroundTexture() {
        return backgroundTexture;
    }

    /**
     *
     * @return
     */
    public TerrainTexture getrTexture() {
        return rTexture;
    }

    /**
     *
     * @return
     */
    public TerrainTexture getgTexture() {
        return gTexture;
    }

    /**
     *
     * @return
     */
    public TerrainTexture getbTexture() {
        return bTexture;
    }

    /**
     *
     * @param backgroundTexture
     */
    public void setBackgroundTexture(TerrainTexture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    /**
     *
     * @param rTexture
     */
    public void setrTexture(TerrainTexture rTexture) {
        this.rTexture = rTexture;
    }

    /**
     *
     * @param gTexture
     */
    public void setgTexture(TerrainTexture gTexture) {
        this.gTexture = gTexture;
    }

    /**
     *
     * @param bTexture
     */
    public void setbTexture(TerrainTexture bTexture) {
        this.bTexture = bTexture;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     *
     * @param backgroundTexture
     * @param rTexture
     * @param gTexture
     * @param bTexture
     */
    public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture, TerrainTexture bTexture) {
        this.backgroundTexture = backgroundTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
    }
//</editor-fold>

}
