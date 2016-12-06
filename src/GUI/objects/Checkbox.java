/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIElement;
import GUI.lib.MouseActor;
import GUI.lib.Renderable;
import rx.Observable;
import userInput.MouseState;

/**
 * TODO
 * @author Blackened
 */
public class Checkbox extends MouseActor implements Renderable{

    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GUIElement getGUIElement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
