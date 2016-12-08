/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * An abstract class that partly defines all shader programs.
 *
 * @author Blackened
 */
public abstract class ShaderProgram {

    /**
     * The ID of the shader program.
     */
    private final int programID;

    /**
     * The ID of the vertex shader.
     */
    private final int vertexShaderID;

    /**
     * The ID of the fragment shader.
     */
    private final int fragmentShaderID;

    /**
     * A map of all single (e.g. no arrays) uniform variable locations mapped to
     * their name.
     */
    protected Map<String, Integer> uniformLocations = new HashMap<>();

    /**
     * Creates a new instance of ShaderProgram, and links the program.
     *
     * @param vertexFile The relative location of the vertex shader file.
     * @param fragmentFile The relative location of the fragment shader file.
     */
    public ShaderProgram(String vertexFile, String fragmentFile) {
        this.vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        this.fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

        this.programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindVariablesAndValidate();
    }

    /**
     * Binds the <b>in<b> variables of the vertex shader, the uniform variables,
     * and links and validates both shaders.
     */
    private void bindVariablesAndValidate() {
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    /**
     * Will get all uniform locations by calling the getUniformLocation method
     * for all uniform variables needed.
     */
    protected abstract void getAllUniformLocations();

    /**
     * Get the location for a uniform variable.
     *
     * @param uniformName The name of the uniform variable for which the
     * location will be returned.
     * @return The location of a uniform variable specified by the name.
     */
    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    /**
     * Starts the program.
     */
    public final void start() {
        GL20.glUseProgram(programID);
    }

    /**
     * Stops the program.
     */
    public final void stop() {
        GL20.glUseProgram(0);
    }

    /**
     * Cleans the memory from all shaders and the program itself.
     */
    public final void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    /**
     * Will bind all necessary attributes by calling the bindAttribute method
     * for all of them.
     */
    protected abstract void bindAttributes();

    /**
     * Will take in the number of the attribute list in the VAO and bind it to
     * the variable name in the shader.
     *
     * @param attribute The number of the attribute list in the VAO.
     * @param variableName The name of the variable in the shader.
     */
    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    /**
     * Loads a float value to the uniform variable in the specified location.
     *
     * @param location The location of the uniform variable to which the float
     * needs to be loaded.
     * @param value The value that needs to be loaded into the uniform variable.
     */
    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    /**
     * Loads an integer value to the uniform variable in the specified location.
     *
     * @param location The location of the uniform variable to which the integer
     * needs to be loaded.
     * @param value The value that needs to be loaded into the uniform variable.
     */
    protected void loadInt(int location, int value) {
        GL20.glUniform1i(location, value);
    }

    /**
     * Loads a vector value to the uniform variable in the specified location.
     *
     * @param location The location of the uniform variable to which the float
     * needs to be loaded.
     * @param vector The value that needs to be loaded into the uniform
     * variable.
     */
    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    /**
     * Loads a boolean value to the uniform variable in the specified location
     * by converting it to a float first. 1 equals true, 0 equals false;
     *
     * @param location The location of the uniform variable to which the boolean
     * needs to be loaded.
     * @param value The value that needs to be loaded into the uniform variable.
     */
    protected void loadBoolean(int location, boolean value) {
        float toLoad = 0;
        if (value) {
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);
    }

    /**
     * Loads a matrix to the uniform variable in the specified location by
     * converting it to a float buffer first.
     *
     * @param location The location of the uniform variable to which the matrix
     * needs to be loaded.
     * @param matrix The matrix that needs to be loaded into the uniform
     * variable.
     */
    protected void loadMatrix(int location, Matrix4f matrix) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    /**
     * Loads the shader given its content and type.
     *
     * @param file The content of the shader file.
     * @param type The type of shader (lwjgl!).
     * @return The shader ID.
     */
    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(ShaderProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.GL_COMPILE_STATUS);
            System.out.println("Shader of type: " + type + " did not compile!");
        }
        return shaderID;

    }
}
