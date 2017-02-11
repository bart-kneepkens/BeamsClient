/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import dataAccess.lwjgl.Loader;
import dataAccess.lwjgl.ModelData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import game.entity.models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class OBJLoader {

    private static Map<String, ModelData> modelCache = new HashMap<>();

    /**
     * Loads all vertices, normals, texture-coordinates and indices from a file.
     *
     * @param fileName The name of the file, extension not included.
     * @return
     */
    public static RawModel loadObjModel(String fileName) {
        if (modelCache.containsKey(fileName)) {
            return Loader.loadToVAO(modelCache.get(fileName));
        } else {
            FileReader fr = null;
            try {
                fr = new FileReader(new File("res/models/" + fileName));
            } catch (FileNotFoundException ex) {
                System.out.println("Couldn't load .obj file!");
                Logger.getLogger(OBJLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

            BufferedReader reader = new BufferedReader(fr);
            String line;
            List<Vector3f> vertices = new ArrayList<>();
            List<Vector2f> textures = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();
            float[] verticesArray = null;
            float[] normalsArray = null;
            float[] textureArray = null;
            int[] indicesArray = null;
            try {
                while (true) {
                    line = reader.readLine();
                    String[] currentLine = line.split(" ");
                    if (line.startsWith("v ")) {
                        Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                        vertices.add(vertex);
                    } else if (line.startsWith("vt ")) {
                        Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                        textures.add(texture);
                    } else if (line.startsWith("vn ")) {
                        Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                        normals.add(normal);
                    } else if (line.startsWith("f ")) {
                        textureArray = new float[vertices.size() * 2];
                        normalsArray = new float[vertices.size() * 3];
                        break;
                    }
                }
                while (line != null) {
                    if (!line.startsWith("f ")) {
                        line = reader.readLine();
                        continue;
                    }
                    String[] currentLine = line.split(" ");
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");

                    processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }

            verticesArray = new float[vertices.size() * 3];
            indicesArray = new int[indices.size()];

            int vertexPointer = 0;
            for (Vector3f vertex : vertices) {
                verticesArray[vertexPointer++] = vertex.x;
                verticesArray[vertexPointer++] = vertex.y;
                verticesArray[vertexPointer++] = vertex.z;
            }

            for (int i = 0; i < indices.size(); i++) {
                indicesArray[i] = indices.get(i);
            }

            ModelData modelData = new ModelData(verticesArray, textureArray, normalsArray, indicesArray);
            modelCache.put(fileName, modelData);

            return Loader.loadToVAO(modelData);
        }

    }

    public static RawModel loadObjModel(File file) {
        if (modelCache.containsKey(file.getAbsolutePath())) {
            return Loader.loadToVAO(modelCache.get(file.getAbsolutePath()));
        } else {
            FileReader fr = null;
            try {
                fr = new FileReader(file);
            } catch (FileNotFoundException ex) {
                System.out.println("Couldn't load .obj file!");
                Logger.getLogger(OBJLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

            BufferedReader reader = new BufferedReader(fr);
            String line;
            List<Vector3f> vertices = new ArrayList<>();
            List<Vector2f> textures = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();
            float[] verticesArray = null;
            float[] normalsArray = null;
            float[] textureArray = null;
            int[] indicesArray = null;
            try {
                while (true) {
                    line = reader.readLine();
                    String[] currentLine = line.split(" ");
                    if (line.startsWith("v ")) {
                        Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                        vertices.add(vertex);
                    } else if (line.startsWith("vt ")) {
                        Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                        textures.add(texture);
                    } else if (line.startsWith("vn ")) {
                        Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                        normals.add(normal);
                    } else if (line.startsWith("f ")) {
                        textureArray = new float[vertices.size() * 2];
                        normalsArray = new float[vertices.size() * 3];
                        break;
                    }
                }
                while (line != null) {
                    if (!line.startsWith("f ")) {
                        line = reader.readLine();
                        continue;
                    }
                    String[] currentLine = line.split(" ");
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");

                    processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }

            verticesArray = new float[vertices.size() * 3];
            indicesArray = new int[indices.size()];

            int vertexPointer = 0;
            for (Vector3f vertex : vertices) {
                verticesArray[vertexPointer++] = vertex.x;
                verticesArray[vertexPointer++] = vertex.y;
                verticesArray[vertexPointer++] = vertex.z;
            }

            for (int i = 0; i < indices.size(); i++) {
                indicesArray[i] = indices.get(i);
            }
            ModelData modelData = new ModelData(verticesArray, textureArray, normalsArray, indicesArray);
            modelCache.put(file.getAbsolutePath(), modelData);

            return Loader.loadToVAO(modelData);
        }

    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTex.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
    }
}
