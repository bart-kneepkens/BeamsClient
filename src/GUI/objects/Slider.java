/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIElement;
import GUI.lib.MouseActor;
import rx.Observable;
import userInput.MouseState;
import GUI.lib.GUIRenderable;

/**
 * TODO
 * @author Blackened
 */
public class Slider extends MouseActor implements GUIRenderable{

    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GUIElement getGUIElement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
