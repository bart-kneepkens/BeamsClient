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

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public boolean isActive();

    public void setActive(boolean value);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void update();
//</editor-fold>

}
