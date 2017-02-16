/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.objects;

import gui.lib.GUIParent;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import gui.lib.Event;
import gui.lib.MouseInput;

/**
 *
 * @author Blackened
 */
public class Window extends Container {

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Window(GUIParent parent) throws IOException {
        super(parent, 400, 500, new Vector2f(25, 300), 0);
        super.loadBackground("testWindow");
        super.setPaddingLeft(33);
        super.setPaddingTop(32);
        super.enableBackground();
        Button buttonClose = new Button(this, 20, 20,
                new Vector2f(this.getGUIElement().getWidth() - 63, -5),
                1);
        buttonClose.loadTextureAtlas("buttons/cross_Atlas");
        buttonClose.onClick(x -> this.buttonClose_Click(x));
        buttonClose.subscribe(MouseInput.getMouseSubject());
        this.addChild(buttonClose);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public final Window loadWindow() {
        this.load();
        return this;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    private void buttonClose_Click(Event event) {
        super.hide();
    }
//</editor-fold>

}
