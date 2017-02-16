/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess.lwjgl;

import toolbox.Convert;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import game.entity.models.RawModel;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import static toolbox.AttributeListPosition.NORMAL_VECTORS;
import static toolbox.AttributeListPosition.TEXTURE_COORDS;
import static toolbox.AttributeListPosition.VERTEX_POSITIONS;
import toolbox.Settings;

/**
 * An instance of this class is responsible for loading data into a VAO and
 * creating a VAO.
 *
 * @author Blackened
 */
public class Loader {

    //<editor-fold defaultstate="collapsed" desc="Static Properties">
    /**
     * Keeps track of all VAO's, VBO's and textures, so they can be deleted from
     * memory once the program exits.
     */
    private static final List<Integer> vaos = new ArrayList<>();
    private static final List<Integer> vbos = new ArrayList<>();
    private static final Map<String, Integer> textureMap = new HashMap<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Static Methods">
    /**
     * Creates a new VAO, binds the data to one of the attribute lists.
     *
     * @param modelData The data to be bound to the VAO.
     * @return A new instance of RawModel containing the VAO ID and vertex
     * count.
     */
    public static RawModel loadToVAO(ModelData modelData) {
        int vaoID = createVAO();
        unbindVAO();
        bindIndicesBuffer(vaoID, modelData.getIndices());
        storeDataInAttributeList(vaoID, VERTEX_POSITIONS, 3, modelData.getVertexPositions());
        storeDataInAttributeList(vaoID, TEXTURE_COORDS, 2, modelData.getTextureCoords());
        storeDataInAttributeList(vaoID, NORMAL_VECTORS, 3, modelData.getNormals());

        RawModel rawModel = new RawModel(vaoID, modelData.getIndices().length);

        return rawModel;
    }

    /**
     * Used for fonts so far!
     *
     * @param positions
     * @param textureCoords
     * @return
     */
    public static int loadToVAO(float[] positions, float[] textureCoords) {
        int vaoID = createVAO();
        unbindVAO();
        storeDataInAttributeList(vaoID, VERTEX_POSITIONS, 2, positions);
        storeDataInAttributeList(vaoID, TEXTURE_COORDS, 2, textureCoords);

        return vaoID;
    }

    public static int loadTexture(File file) {
        int textureID;
        if (textureMap.containsKey(file.getAbsolutePath())) {
            textureID = textureMap.get(file.getAbsolutePath());
        } else {
            Texture texture = null;
            try {
                texture = TextureLoader.getTexture("PNG", new FileInputStream(file));

            } catch (IOException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
            textureID = texture.getTextureID();
            textureMap.put(file.getAbsolutePath(), textureID);
        }
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0f);
        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            if (Settings.ANISOTROPIC_FILTERING) {
                float amount = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
            }
        } else {
            System.out.println("no anisotropic filtering possible!");
        }
        return textureID;
    }

    /**
     * Creates a new VAO.
     *
     * @return Its ID that points to the VAO in memory.
     */
    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        // Adds VAO to list so that it can be cleared when needed.
        vaos.add(vaoID);
        return vaoID;
    }

    /**
     * Stores data in an attribute list of a VAO.
     *
     * @param vaoID The id of the VAO to which data will be added.
     * @param attributeNumber The number of the attribute list in which the data
     * will be stored.
     * @param data The data that will be stored in the attribute list.
     */
    private static void storeDataInAttributeList(int vaoID, int attributeNumber, int coordinateSize, float[] data) {
        // bind VAO so that it can be used.
        bindVAO(vaoID);

        // Create new VBO.
        int vboID = GL15.glGenBuffers();

        // Adds VBO to list so that it can be cleared when needed.
        vbos.add(vboID);

        // VBO has to be bound aswel.
        bindArrayBuffer(vboID);

        // Converts float array to an instance of FloatBuffer, which can
        // be stored in a VBO.
        FloatBuffer buffer = Convert.toReadableFloatBuffer(data);

        // Puts the buffer into the VBO, and GL_STATIC_DRAW tells it that it 
        // won't ever be modified.
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Specifies that this is for the Vertex Array.
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);

        // Unbind the VBO.
        unbindArrayBuffer();

        // unbind VAO so that another may be bound.
        unbindVAO();
    }

    /**
     * Stores data in an attribute list of a VAO.
     *
     * @param vaoID The id of the VAO to which data will be added.
     * @param attributeNumber The number of the attribute list in which the data
     * will be stored.
     * @param data The data that will be stored in the attribute list.
     */
    private static void bindIndicesBuffer(int vaoID, int[] data) {
        // bind VAO so that it can be used.
        bindVAO(vaoID);

        // Create new VBO.
        int vboID = GL15.glGenBuffers();

        // Adds VBO to list so that it can be cleared when needed.
        vbos.add(vboID);

        // VBO has to be bound aswel.
        bindElementArrayBuffer(vboID);

        // Converts int array to an instance of IntBuffer, which can
        // be stored in a VBO.
        IntBuffer buffer = Convert.toReadableIntBuffer(data);

        // Puts the buffer into the VBO, and GL_STATIC_DRAW tells it that it 
        // won't ever be modified.
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Unbind the VBO.
        //unbindElementArrayBuffer();
        // unbind VAO so that another may be bound.
        unbindVAO();
    }

    /**
     * Unbinds the VAO.
     */
    private static void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    /**
     * Binds the VAO, so that it can be modified.
     *
     * @param vaoID The ID of the VAO that will be bound.
     */
    private static void bindVAO(int vaoID) {
        GL30.glBindVertexArray(vaoID);
    }

    /**
     * Binds the VBO, so that it can be modified.
     *
     * @param vboID The ID of the VBO that will be bound.
     */
    private static void bindArrayBuffer(int vboID) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
    }

    /**
     * Binds the VBO, so that it can be modified.
     *
     * @param vboID The ID of the VBO that will be bound.
     */
    private static void bindElementArrayBuffer(int vboID) {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
    }

    /**
     * Unbinds the VBO.
     */
    private static void unbindElementArrayBuffer() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * Unbinds the VBO.
     */
    private static void unbindArrayBuffer() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Cleans the memory of all VAO's and VBO's.
     */
    public static void cleanUp() {
        vaos.forEach(x -> GL30.glDeleteVertexArrays(x));
        vbos.forEach(x -> GL15.glDeleteBuffers(x));
        textureMap.values().forEach(x -> GL11.glDeleteTextures(x));
    }
    //</editor-fold>

}
