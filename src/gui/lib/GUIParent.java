/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.lib;

import java.util.Collection;

/**
 *
 * @author Blackened
 */
public interface GUIParent {

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Collection<GUIRenderable> getChildren();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void addChild(GUIRenderable child);

    public void removeChild(GUIRenderable child);

    public void addChildren(Collection<GUIRenderable> children);
//</editor-fold>

}
