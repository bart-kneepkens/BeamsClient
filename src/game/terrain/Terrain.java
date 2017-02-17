/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.terrain;

import dataAccess.lwjgl.ModelData;
import java.awt.image.BufferedImage;
import game.entity.models.RawModel;
import org.lwjgl.util.vector.Vector3f;
import dataAccess.lwjgl.VAO_Loader;
import org.lwjgl.util.vector.Vector2f;
import game.terrain.texture.TerrainTexture;
import game.terrain.texture.TerrainTexturePack;
import toolbox.Maths;

/**
 *
 * @author Blackened
 */
public class Terrain {

    //<editor-fold defaultstate="collapsed" desc="Static Properties">
    public static float SIZE = 80;
    public static float BORDER_SIZE = 2;
    public static float MAX_HEIGHT = 20;
    public static float MAX_PIXEL_COLOUR = 256 * 256 * 256;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private float xCoord;
    private float zCoord;

    private float[][] heightTable;

    /**
     * The Euler rotation.
     */
    private Vector3f rotation;

    private RawModel model;
    private TerrainTexturePack texturePack;
    private TerrainTexture blendMap;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public float getX() {
        return xCoord;
    }

    public float getZ() {
        return zCoord;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setTexturePack(TerrainTexturePack texturePack) {
        this.texturePack = texturePack;
    }

    public void setBlendMap(TerrainTexture blendMap) {
        this.blendMap = blendMap;
    }

    public void setModel(RawModel model) {
        this.model = model;
    }

    public void setHeightTable(float[][] heights) {
        this.heightTable = heights;
    }

    public RawModel getModel() {
        return model;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Terrain(float gridX, float gridZ, TerrainTexturePack texturePack, TerrainTexture blendMap, BufferedImage heightMap) {
        this.xCoord = gridX * SIZE;
        this.zCoord = gridZ * SIZE;
        this.rotation = new Vector3f(0, 0, 0);
        this.texturePack = texturePack;
        this.blendMap = blendMap;
        this.model = generateTerrain(heightMap);
    }

    public Terrain() {
        this.xCoord = 0;
        this.zCoord = 0;
        this.rotation = new Vector3f(0, 0, 0);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public float getHeightOfTerrain(float worldX, float worldZ) {
        float terrainX = worldX - this.xCoord;
        float terrainZ = worldZ - this.zCoord;
        float gridSquareSize = SIZE / ((float) heightTable.length - 1);
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
        if (gridX >= heightTable.length - 1 || gridZ >= heightTable.length - 1 || gridX < 0 || gridZ < 0) {
            return 0;
        }
        float x = (terrainX % gridSquareSize) / gridSquareSize;
        float z = (terrainZ % gridSquareSize) / gridSquareSize;
        float answer;
        if (x <= (1 - z)) {
            answer = Maths.barryCentric(new Vector3f(0, heightTable[gridX][gridZ], 0), new Vector3f(1,
                    heightTable[gridX + 1][gridZ], 0), new Vector3f(0,
                    heightTable[gridX][gridZ + 1], 1), new Vector2f(x, z));
        } else {
            answer = Maths.barryCentric(new Vector3f(1, heightTable[gridX + 1][gridZ], 0), new Vector3f(1,
                    heightTable[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                    heightTable[gridX][gridZ + 1], 1), new Vector2f(x, z));
        }
        return answer;

    }

    /**
     * Increases the rotation of the entity.
     *
     * @param vector The vector that translates the rotation.
     * <b>Note:</b> this vector can not be normalized!
     */
    public void increaseRotation(Vector3f vector) {
        this.rotation.x += vector.x;
        this.rotation.y += vector.y;
        this.rotation.z += vector.z;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Static Methods">
    public static RawModel generateTerrain(BufferedImage heightMap) {

        int VERTEX_COUNT = heightMap.getHeight();

        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = getHeight(j, i, heightMap);
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
                Vector3f normal = calculateNormal(j, i, heightMap);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return VAO_Loader.loadToVAO(new ModelData(vertices, textureCoords, normals, indices));
    }

    public static float[][] generateHeightTable(BufferedImage heightMap) {
        int VERTEX_COUNT = heightMap.getHeight();
        float[][] heightTable = new float[VERTEX_COUNT][VERTEX_COUNT];

        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                float height = getHeight(j, i, heightMap);
                heightTable[j][i] = height;
            }
        }
        return heightTable;
    }

    private static float getHeight(int x, int z, BufferedImage image) {
        if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
            return 0;
        }
        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOUR / 2f;
        height /= MAX_PIXEL_COLOUR / 2f;
        height *= MAX_HEIGHT;
        return height;

    }

    private static Vector3f calculateNormal(int x, int z, BufferedImage image) {
        float heightL = getHeight(x - 1, z, image);
        float heightR = getHeight(x + 1, z, image);
        float heightD = getHeight(x, z - 1, image);
        float heightU = getHeight(x, z + 1, image);
        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }

    //</editor-fold>
}
