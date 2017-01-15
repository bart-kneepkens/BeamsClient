/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIParent;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class Window extends Container{
    
    public Window(GUIParent parent) throws IOException {
        super(parent, 400, 500, new Vector2f(25,300), 0);
        super.loadBackground("testWindow");
        super.setPaddingLeft(33);
        super.setPaddingTop(32);
        super.enableBackground();
        Button buttonClose = new Button(this, 20, 20, 
                new Vector2f(this.getGUIElement().getWidth() - 63, -5)
                , 1);
        buttonClose.loadTextureAtlas("buttons/cross_Atlas");
        buttonClose.onClick(x -> this.buttonClose_Click(x));
        buttonClose.subscribe(MouseInput.getMouseSubject());
        this.addChild(buttonClose);
    }
    
    private void buttonClose_Click(Event event){
        super.hide();
    }
    
    public final Window loadWindow(){
        this.load();
        return this;
    }
}
