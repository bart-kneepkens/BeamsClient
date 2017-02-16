/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import dataAccess.lwjgl.Loader;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import game.entity.models.RawModel;
import game.terrain.Terrain;
import game.terrain.texture.TerrainTexture;
import game.terrain.texture.TerrainTexturePack;

/**
 *
 * @author Blackened
 */
public class FileLoader {

    //<editor-fold defaultstate="collapsed" desc="Static Methods">
    /**
     * Loads a file with .ter extension. Creates a terrain object with all
     * information found in this file.
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Terrain loadTerrain(File file) throws FileNotFoundException, IOException {
        System.out.println("Loading terrain file...");
        Terrain terrain = new Terrain();

        float[][] heightTable = null;
        RawModel model = null;
        TerrainTexture blendMap = null;
        TerrainTexture bgTexture = null;
        TerrainTexture rTexture = null;
        TerrainTexture gTexture = null;
        TerrainTexture bTexture = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int lineNumber = 0;
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.startsWith("Size: ")) {
                    Terrain.SIZE = Float.parseFloat(line.split(": ")[1]);
                }
                if (line.startsWith("Height: ")) {
                    Terrain.MAX_HEIGHT = Float.parseFloat(line.split(": ")[1]);
                }
                if (line.startsWith("HeightMap: ")) {
                    model = Terrain.generateTerrain(loadBufferedImage(new File(file.getAbsoluteFile().getParentFile().getAbsolutePath() + "/" + line.split(": ")[1])));
                    heightTable = Terrain.generateHeightTable(loadBufferedImage(new File(file.getAbsoluteFile().getParentFile().getAbsolutePath() + "/" + line.split(": ")[1])));
                }
                if (line.startsWith("BlendMap: ")) {
                    blendMap = new TerrainTexture(Loader.loadTexture(new File(file.getAbsoluteFile().getParentFile().getAbsolutePath() + "/" + line.split(": ")[1])));
                }
                if (line.startsWith("bgTexture: ")) {
                    bgTexture = new TerrainTexture(Loader.loadTexture(new File(file.getAbsoluteFile().getParentFile().getAbsolutePath() + "/" + line.split(": ")[1])));
                }
                if (line.startsWith("rTexture: ")) {
                    rTexture = new TerrainTexture(Loader.loadTexture(new File(file.getAbsoluteFile().getParentFile().getAbsolutePath() + "/" + line.split(": ")[1])));
                }
                if (line.startsWith("gTexture: ")) {
                    gTexture = new TerrainTexture(Loader.loadTexture(new File(file.getAbsoluteFile().getParentFile().getAbsolutePath() + "/" + line.split(": ")[1])));
                }
                if (line.startsWith("bTexture: ")) {
                    bTexture = new TerrainTexture(Loader.loadTexture(new File(file.getAbsoluteFile().getParentFile().getAbsolutePath() + "/" + line.split(": ")[1])));
                }
            }

            if (model == null || heightTable == null || blendMap == null || bgTexture == null || rTexture == null || gTexture == null || bTexture == null) {
                System.out.println("But went wrong!");
            }
            terrain.setModel(model);
            terrain.setHeightTable(heightTable);
            terrain.setBlendMap(blendMap);
            TerrainTexturePack texturePack = new TerrainTexturePack(bgTexture, rTexture, gTexture, bTexture);
            terrain.setTexturePack(texturePack);

            System.out.println("Terrain file successfully loaded.");
            return terrain;
        }

    }

    /**
     * Loads a buffered image
     *
     * @param file
     * @return
     */
    private static BufferedImage loadBufferedImage(File file) throws IOException {
        BufferedImage image = null;
        return image = ImageIO.read(file);
    }
//</editor-fold>
}
