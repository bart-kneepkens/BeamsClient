/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

/**
 *
 * @author Blackened
 */
public interface Autonomous {
    
    void update();
    
    boolean isActive();
    
    void setActive(boolean value);
    
}
