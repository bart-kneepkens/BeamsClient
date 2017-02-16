/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

/**
 * Conversion class for render engine.
 *
 * @author Blackened
 */
public class Convert {

    //<editor-fold defaultstate="collapsed" desc="Static Methods">
    /**
     * Converts a float array to a readable float buffer.
     *
     * @param data The array to be converted.
     * @return A readable instance of the FloatBuffer class.
     */
    public static FloatBuffer toReadableFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        // Make readable
        buffer.flip();
        return buffer;
    }

    public static IntBuffer toReadableIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        // Make readable
        buffer.flip();
        return buffer;
    }
//</editor-fold>

}
