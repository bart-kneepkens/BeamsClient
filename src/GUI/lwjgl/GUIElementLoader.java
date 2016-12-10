/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.lwjgl;

import GUI.lib.GUIElement;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import static toolbox.AttributeListPosition.*;
import toolbox.Convert;

/**
 * hoi
 * @author Blackened
 */
public class GUIElementLoader {

    /**
     * Keeps track of all VAO's, VBO's and textures, so they can be deleted from
     * memory once the program exits.
     */
    private static List<Integer> vaos = new ArrayList<>();
    private static List<Integer> vbos = new ArrayList<>();
    private static List<Integer> textures = new ArrayList<>();

    /**
     * Creates a new VAO, binds the data to one of the attribute lists.
     *
     * @param guiElement
     * @return
     */
    public static int loadToVAO(GUIElement guiElement) {
        int vaoID = createVAO();
        unbindVAO();
        storeDataInAttributeList(vaoID, VERTEX_POSITIONS, 3, guiElement.getOrigin());
        storeDataInAttributeList(vaoID, TEXTURE_COORDS, 2, guiElement.getTextureCoords());

        return vaoID;
    }
    
    public static void reloadTextureCoords(GUIElement guiElement){
        storeDataInAttributeList(guiElement.getVaoID(), TEXTURE_COORDS, 2, guiElement.getTextureCoords());
        
    }

    public static int loadTexture(String fileName) throws IOException {
        Texture texture = null;
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/" + fileName + ".png"));

        int textureID = texture.getTextureID();
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
        textures.forEach(x -> GL11.glDeleteTextures(x));
    }

}
