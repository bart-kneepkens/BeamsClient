/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import beamsClient.BeamsClient;
import static beamsClient.BeamsClient.loader;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import models.RawModel;
import terrain.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

/**
 *
 * @author Blackened
 */
public class FileLoader {

    public static Terrain loadTerrain(File file) throws FileNotFoundException, IOException {
        Terrain terrain = new Terrain();

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
                    model = Terrain.generateTerrain(BeamsClient.loader, loadBufferedImage(new File(line.split(": ")[1])));
                }
                if (line.startsWith("BlendMap: ")) {
                    blendMap = new TerrainTexture(loader.loadTexture(new File(line.split(": ")[1])));
                }
                if (line.startsWith("bgTexture: ")) {
                    bgTexture = new TerrainTexture(loader.loadTexture(new File(line.split(": ")[1])));
                }
                if (line.startsWith("rTexture: ")) {
                    rTexture = new TerrainTexture(loader.loadTexture(new File(line.split(": ")[1])));
                }
                if (line.startsWith("gTexture: ")) {
                    gTexture = new TerrainTexture(loader.loadTexture(new File(line.split(": ")[1])));
                }
                if (line.startsWith("bTexture: ")) {
                    bTexture = new TerrainTexture(loader.loadTexture(new File(line.split(": ")[1])));
                }
            }
            
            System.out.println("File reading complete!");
            
            if (model == null || blendMap == null || bgTexture == null || rTexture == null || gTexture == null || bTexture == null){
                System.out.println("But went wrong!");
            }
            terrain.setModel(model);
            terrain.setBlendMap(blendMap);
            TerrainTexturePack texturePack = new TerrainTexturePack(bgTexture, rTexture, gTexture, bTexture);
            terrain.setTexturePack(texturePack);
            
            return terrain;
        }
        
    }

    private static BufferedImage loadBufferedImage(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);

        } catch (IOException ex) {
            Logger.getLogger(Terrain.class.getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }

}
