/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
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

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;
    private static final String TITLE = "Beams Display";

    /**
     * Creates a new display, with width, height and the title specified as
     * static variables to the display manager.
     */
    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            //Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setFullscreen(true);
            Display.setTitle(TITLE);
        } catch (LWJGLException ex) {
            Logger.getLogger(DisplayManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);

    }

    /**
     * Updates the display with an fps cap set as static variables to the
     * display manager.
     */
    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
    }

    /**
     * Closes the lwjgl display.
     */
    public static void closeDisplay() {
        Display.destroy();
    }

}
