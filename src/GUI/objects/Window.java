/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.UserInterface;
import GUI.lwjgl.GUIElementLoader;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import userInput.Event;
import userInput.MouseInput;

/**
 *
 * @author Blackened
 */
public class Window extends Container{
    
    public Window(UserInterface userInterface) throws IOException {
        super(400, 500, new Vector2f(25,300), 0, userInterface);
        this.getGUIElement().setTextureID(GUIElementLoader.loadTexture("window"));
        super.userInterface = userInterface;
        Button buttonClose = new Button(20, 20, 
                new Vector2f(this.getGUIElement().getWidth() - 40,this.getGUIElement().getHeight() - 40)
                , -1);
        buttonClose.loadTextureAtlas("buttons/cross_Atlas");
        buttonClose.onClick(x -> this.buttonClose_Click(x));
        buttonClose.subscribe(MouseInput.getMouseSubject());
        this.addChild(buttonClose, true);
        this.setRendered(true);
    }
    
    private void buttonClose_Click(Event event){
        super.close();
    }
    
    public final Window loadWindow(){
        this.load();
        return this;
    }
}
