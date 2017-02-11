/*
 * An OpenGL GameEngine test project.
 * By BlackenedSandman - marcvandeuren@gmail.com
 */
package game.camera;

import org.lwjgl.util.vector.Vector3f;

/**
 * Abstract camera class for viewing a 3D scene.
 * @author Blackened
 */
public abstract class Camera {

    /**
     * The exact position of the camera.
     */
    protected final Vector3f position = new Vector3f(0, 0, 0);

    /**
     * The vertical angle of the camera.
     */
    protected float pitch;

    /**
     * The default pitch for the camera objects.
     */
    public static float MINIMUM_PITCH = 10;

    /**
     * The horizontal angle of the camera (around the y-axis).
     */
    protected float yaw = 0;

    /**
     * The sideways vertical angle of the camera.
     */
    protected float roll;

    /**
     * Increases the position of this camera object by the given amount.
     * @param amount A 3D vector that contains the amounts that have to be added 
     * onto the position.
     */
    public final void increasePosition(Vector3f amount) {
        this.position.x += amount.x;
        this.position.y += amount.y;
        this.position.z += amount.z;
    }

    /**
     * Getter for the position of this camera object.
     * @return The position of this camera object.
     */
    public final Vector3f getPosition() {
        return position;
    }

    /**
     * Getter for the pitch of this camera object.
     * @return The pitch of this camera object (vertical angle).
     */
    public final float getPitch() {
        return pitch;
    }

    /**
     * Getter for the yaw of this camera object.
     * @return The yaw of this camera object (horizontal angle).
     */
    public final float getYaw() {
        return yaw;
    }

    /**
     * Getter for the roll of this camera object.
     * @return The sideways vertical angle of the camera.
     */
    public final float getRoll() {
        return roll;
    }

    /**
     * Performs all state updates required each frame.
     */
    public abstract void update();

}
