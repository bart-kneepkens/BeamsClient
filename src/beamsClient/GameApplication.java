/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beamsClient;

import org.lwjgl.opengl.Display;

/**
 *
 * @author Blackened
 */
public abstract class GameApplication {
    
    /**
     * Determines whether the program should be running. This variable is
     * checked every frame, and triggers a program close if false.
     */
    private boolean keepRunning = true;

    /**
     * Starts the program loop.
     */
    public final void start() {
        
        setUp();

        while (!Display.isCloseRequested() && keepRunning) {

            update();
            handleInput();
            render();

        }

        cleanUp();
    }

    /**
     * Set up method, will be called once, only when program is started.
     */
    protected abstract void setUp();

    /**
     * Update method, will be called every frame.
     */
    protected abstract void update();

    /**
     * Input handling method, will be called once every frame.
     */
    protected abstract void handleInput();

    /**
     * Render method, will be called once every frame.
     */
    protected abstract void render();

    /**
     * Clean up method, will be called once, only when program is exited.
     */
    protected abstract void cleanUp();

    /**
     * Will exit the program
     */
    public final void exit() {
        this.keepRunning = false;
    }

}
