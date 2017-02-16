/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.objects;

import gui.lib.GUIElement;
import gui.lib.GUIParent;
import gui.lib.MouseActor;
import rx.Observable;
import gui.lib.MouseState;
import gui.lib.GUIRenderable;

/**
 * TODO
 *
 * @author Blackened
 */
public class Slider extends MouseActor implements GUIRenderable {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private GUIParent parent;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    @Override
    public GUIElement getGUIElement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GUIParent getParent() {
        return this.parent;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//</editor-fold>

}
