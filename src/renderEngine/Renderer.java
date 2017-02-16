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

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * This method will have to be called before rendering is begun.
     */
    public void start();

    /**
     * This method will have to be called after rendering has ended.
     */
    public void stop();

    /**
     * This method will have to be called when the renderer is no longer used.
     */
    public void cleanUp();

    /**
     * This method will have to be called to render an object of type T.
     *
     * @param object The object to be rendered.
     */
    public void render(T object);
//</editor-fold>

}
