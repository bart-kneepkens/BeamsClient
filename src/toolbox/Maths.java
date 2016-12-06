/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import Game.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Contains multiple logic methods.
 * @author Blackened
 */
public class Maths {
    
    /**
     * Creates a matrix that contains the translation, rotation and scale 
     * properties.
     * @param translation The position.
     * @param rotation The Euler rotation.
     * @param scale The scale.
     * @return A new transformation matrix.
     */
    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate(rotation.x, new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate(rotation.y, new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate(rotation.z, new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }
    
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector3f rotation, float scaleX, float scaleY){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        matrix.translate(translation);
        Matrix4f.rotate(rotation.x, new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate(rotation.y, new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate(rotation.z, new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scaleX, scaleY, 1.0f), matrix, matrix);
        
        return matrix;
    }
    
    /**
     * Creates a matrix that contains the translation, rotation and scale
     * properties relative to the camera's position and angle.
     * @param camera The camera object.
     * @return A new view matrix.
     */
    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0,0,1), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }
    
}