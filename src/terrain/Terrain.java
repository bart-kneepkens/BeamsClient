/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terrain;

import DataAccess.ModelData;
import java.awt.image.BufferedImage;
import models.RawModel;
import org.lwjgl.util.vector.Vector3f;
import DataAccess.lwjgl.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

/**
 *
 * @author Blackened
 */
public class Terrain {

    public static float SIZE = 80;
    public static float MAX_HEIGHT = 20;
    public static float MAX_PIXEL_COLOUR = 256 * 256 * 256;

    private float xCoord;
    private float zCoord;

    /**
     * The Euler rotation.
     */
    private Vector3f rotation;

    private RawModel model;
    private TerrainTexturePack texturePack;
    private TerrainTexture blendMap;

    public Terrain(float gridX, float gridZ, TerrainTexturePack texturePack, TerrainTexture blendMap, BufferedImage heightMap) {
        this.xCoord = gridX * SIZE;
        this.zCoord = gridZ * SIZE;
        this.rotation = new Vector3f(0,0,0);
        this.texturePack = texturePack;
        this.blendMap = blendMap;
        this.model = generateTerrain(heightMap);
    }

    public Terrain() {
        this.xCoord = -1f * SIZE;
        this.zCoord = -0.5f * SIZE;
        this.rotation = new Vector3f(0,0,0);
    }
    
    

    public static RawModel generateTerrain(BufferedImage heightMap){
        
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
                vertices[vertexPointer * 3 + 1] = getHeight(j,i,heightMap);
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
        return Loader.loadToVAO(new ModelData(vertices, textureCoords, normals, indices));
    }
    
    private static float getHeight(int x, int z, BufferedImage image){
        if(x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()){
            return 0;
        }
        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOUR/2f;
        height /= MAX_PIXEL_COLOUR/2f;
        height *= MAX_HEIGHT;
        return height;
        
    }
    
    private static Vector3f calculateNormal(int x, int z, BufferedImage image){
        float heightL = getHeight(x-1, z, image);
        float heightR = getHeight(x+1, z, image);
        float heightD = getHeight(x, z-1, image);
        float heightU = getHeight(x, z+1, image);
        Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }

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
    
    
    
    

    /**
     * Increases the rotation of the entity.
     *
     * @param vector The vector that translates the rotation.
     * @NOTE this vector can not be normalized!
     */
    public void increaseRotation(Vector3f vector) {
        this.rotation.x += vector.x;
        this.rotation.y += vector.y;
        this.rotation.z += vector.z;
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
    
    

}
