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
import org.lwjgl.opengl.PixelFormat;

/**
 *
 * @author Blackened
 */
public class DisplayManager {

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int FPS_CAP = 60;
    
    private static long lastFrameTime;
    private static float delta;
    private static final String TITLE = "Beams";

    /**
     * Creates a new display, with width, height and the title specified as
     * static variables to the display manager.
     */
    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle(TITLE);
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
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
    }
    
    public static float getFrameTimeSeconds(){
        return delta;
    }

    /**
     * Closes the lwjgl display.
     */
    public static void closeDisplay() {
        Display.destroy();
    }
    
    public static long getCurrentTime(){
        return Sys.getTime()*1000/Sys.getTimerResolution();
    }

}
