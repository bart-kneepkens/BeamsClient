/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.lib;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Blackened
 */
public interface GUIParent {
    
    public Collection<GUIRenderable> getChildren();
    
    public void addChild(GUIRenderable child);
    
    public void removeChild(GUIRenderable child);
    
    public void addChildren(Collection<GUIRenderable> children);
    
}
