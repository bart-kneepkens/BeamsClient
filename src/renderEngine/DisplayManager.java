/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

/**
 * This class contains all static methods concerning the context and display.
 *
 * @author Blackened
 */
public class DisplayManager {

    //<editor-fold defaultstate="collapsed" desc="Static Properties">
    public static boolean white = false;

    /**
     * The width of the display in pixels.
     */
    private static final int WIDTH = 1280;

    /**
     * The height of the display in pixels.
     */
    private static final int HEIGHT = 720;

    /**
     * The maximum frames per seconds that will be rendered to the screen.
     */
    public static int FPS_CAP = 120;

    /**
     * The time in milliseconds when last frame was rendered.
     */
    private static long lastFrameTime;

    /**
     * The time it took to render this frame in milliseconds.
     */
    private static float delta;

    private static int amountOfFrames = 0;

    private static float totalTime = 0;

    /**
     * The title of the display.
     */
    private static final String TITLE = "Beams";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Static Methods">
    /**
     * Creates a new display, with width, height and the title specified as
     * static variables to the display manager.
     *
     */
    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat().withSamples(8).withDepthBits(24), attribs);

            Display.setResizable(true);
            Display.setTitle(TITLE);
            GL11.glEnable(GL13.GL_MULTISAMPLE);
            //Display.setVSyncEnabled(true);
            //Display.setFullscreen(true);

        } catch (LWJGLException ex) {
            Logger.getLogger(DisplayManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime = getCurrentTime();

    }

    /**
     * Updates the display with an fps cap set as static variables to the
     * display manager.
     */
    public static void updateDisplay() {

        Display.sync(FPS_CAP);

        if (Display.wasResized()) {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        }

        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
        amountOfFrames++;
        totalTime = delta + totalTime;

        if (amountOfFrames % 1000 == 0) {
            Display.setTitle("Beams (fps: " + String.valueOf(Math.round(amountOfFrames / totalTime)) + ")");
            amountOfFrames = 0;
            totalTime = 0;
        }

    }

    /**
     * Gets the time in seconds it took to render this frame.
     *
     * @return The time it took to render this frame in seconds.
     */
    public static float getFrameTimeSeconds() {
        return delta;
    }

    /**
     * Closes the lwjgl display.
     */
    public static void closeDisplay() {
        Display.destroy();
    }

    /**
     * Gets the current time in milliseconds.
     *
     * @return The current time in milliseconds.
     */
    public static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
//</editor-fold>

}
