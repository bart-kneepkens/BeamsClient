/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

/**
 * Specifies the signature of a renderer class.
 *
 * @author Blackened
 * @param <T>
 */
public interface Renderer<T> {

    /**
     * This method will have to be called before rendering is begun.
     */
    void start();

    /**
     * This method will have to be called after rendering has ended.
     */
    void stop();

    /**
     * This method will have to be called when the renderer is no longer used.
     */
    void cleanUp();

    /**
     * This method will have to be called to render an object of type T.
     * @param object The object to be rendered.
     */
    void render(T object);

}
