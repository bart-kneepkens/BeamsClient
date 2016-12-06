/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

/**
 *
 * @author Blackened
 * @param <T>
 */
public interface Renderer<T> {
    
    void start();
    void stop();
    void cleanUp();
    
    void render(T element);
    
}
