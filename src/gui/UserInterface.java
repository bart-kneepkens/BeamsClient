/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.lib.GUIParent;
import gui.lib.GUIRenderable;
import gui.objects.Button;
import gui.objects.Container;
import gui.objects.SettingsWindow;
import beamsClient.BeamsClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import toolbox.Autonomous;
import gui.lib.Event;
import gui.lib.MouseInput;

/**
 *
 * @author Blackened
 */
public class UserInterface implements Autonomous, GUIParent {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private final List<GUIRenderable> children;

    private boolean active = true;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean value) {
        this.active = value;
    }

    @Override
    public Collection<GUIRenderable> getChildren() {
        return this.children;

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public UserInterface() throws IOException {
        this.children = new ArrayList<>();

        //<editor-fold defaultstate="collapsed" desc="Buttons">
        Button buttonExit = new Button(this, 50, 50, new Vector2f(75, 40), 1);
        buttonExit.loadTextureAtlas("buttons/buttonExit_Atlas");
        buttonExit.subscribe(MouseInput.getMouseSubject());
        buttonExit.onClick(x -> {
            try {
                this.buttonExit_Click(x);
            } catch (IOException ex) {
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Button buttonSettings = new Button(this, 50, 50, new Vector2f(175, 40), 1);
        buttonSettings.loadTextureAtlas("buttons/buttonSettings_Atlas");
        buttonSettings.subscribe(MouseInput.getMouseSubject());
        buttonSettings.onClick(x -> {
            try {
                this.buttonLoadTerrain_Click(x);
            } catch (IOException ex) {
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Container container = new Container(this, 300, 120, new Vector2f(Display.getWidth() / 2 - 150, Display.getHeight() - 120), 1);
        container.loadBackground("dockBackdrop");
        container.enableBackground();
        container.addChild(buttonExit);
        container.addChild(buttonSettings);
        container.load();
        container.show();
        //</editor-fold>
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public void update() {
        // Will be changed:
        MouseInput.checkInputs();
    }

    @Override
    public void addChild(GUIRenderable child) {
        if (!this.children.contains(child)) {
            this.children.add(child);
            Collections.sort(this.children, (GUIRenderable o1, GUIRenderable o2) -> {
                Integer z_index1 = o1.getGUIElement().getZ_index();
                Integer z_index2 = o2.getGUIElement().getZ_index();
                return z_index2.compareTo(z_index1);
            });
        }
    }

    @Override
    public void removeChild(GUIRenderable child) {
        this.children.remove(child);
    }

    @Override
    public void addChildren(Collection<GUIRenderable> children) {
        this.children.addAll(children);
        Collections.sort(this.children, (GUIRenderable o1, GUIRenderable o2) -> {
            Integer z_index1 = o1.getGUIElement().getZ_index();
            Integer z_index2 = o2.getGUIElement().getZ_index();
            return z_index2.compareTo(z_index1);
        });
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    private void buttonExit_Click(Event event) throws IOException {
        BeamsClient.getInstance().exit();
    }

    private void buttonLoadTerrain_Click(Event event) throws IOException {
        new SettingsWindow(this).loadWindow().show();
    }
//</editor-fold>
}
