/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.objects;

import gui.lib.GUIElement;
import gui.lib.GUIParent;
import gui.lib.MouseActor;
import gui.lwjgl.GUIElementLoader;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import rx.Observable;
import gui.lib.MouseState;
import gui.lib.GUIRenderable;
import dataAccess.lwjgl.VAO_Loader;
import gui.font.fontMeshCreator.FontType;
import java.io.File;

/**
 * TODO
 *
 * @author Blackened
 */
public class Checkbox extends MouseActor implements GUIRenderable {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private boolean checked = false;

    private final GUIElement guiElement;

    private GUIParent parent;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    @Override
    public GUIElement getGUIElement() {
        return guiElement;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public GUIParent getParent() {
        return this.parent;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Checkbox(GUIParent parent, int width, int height, Vector2f position, int z_index) throws IOException {
        this.guiElement = new GUIElement(width, height, position, z_index);
        this.loadTextureAtlas("buttons/ckbox");
        this.parent = parent;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void setLabel(String text) {
        FontType font = new FontType(VAO_Loader.loadTexture(new File("res/fonts/arial.png")), new File("res/fonts/arial.fnt"));
        Label label = new Label(text, 0.65f, font, new Vector2f(this.guiElement.getPosition().getX() + this.guiElement.getWidth() + 10, this.guiElement.getPosition().getY()), 0.5f, false);
        label.setColour(1, 1, 1);
        this.guiElement.setLabel(label);
    }

    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        this.setMouseSubscription(inputObservable
                .subscribe(x -> {
                    if (this.getGUIElement().inRange(x)) {
                        if (x.isButton0Down() && this.guiElement.inRange(x.getPressOrigin())) {
                            this.setPressed(x, this, true);
                            this.changeToPressTexture();
                        } else {
                            if (this.isPressed()) {
                                this.checked = !this.checked;
                                this.click(x, this);
                                this.setPressed(x, this, false);
                            }
                            this.hover(x, this);
                            if (this.checked) {
                                this.changeToCheckedTexture();
                            } else {
                                this.changeToUncheckedTexture();
                            }

                        }

                    } else {
                        if (this.checked) {
                            this.changeToCheckedTexture();
                        } else {
                            this.changeToUncheckedTexture();
                        }
                        this.setPressed(x, this, false);
                    }
                },
                        x -> x.printStackTrace()));
    }

    public final void loadTextureAtlas(String name) throws IOException {
        this.guiElement.setTexture(GUIElementLoader.loadTexture(name));
        this.changeToUncheckedTexture();
    }

    public void setChecked(boolean value) {
        this.checked = value;
        if (value) {
            this.changeToCheckedTexture();
        } else {
            this.changeToUncheckedTexture();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Changes the buttons active texture to the main texture.
     */
    private void changeToUncheckedTexture() {
        this.guiElement.loadTextureCoords(new float[]{
            0, 0,
            0, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0,
            0, 0
        });
    }

    /**
     * Changes the buttons active texture to the hover texture.
     */
    private void changeToCheckedTexture() {
        this.guiElement.loadTextureCoords(new float[]{
            0.5f, 0,
            0.5f, 0.5f,
            1f, 0.5f,
            1f, 0.5f,
            1f, 0,
            0.5f, 0
        });
    }

    /**
     * Changes the buttons active texture to the click texture.
     */
    private void changeToPressTexture() {
        this.guiElement.loadTextureCoords(new float[]{
            0f, 0.5f,
            0f, 1f,
            0.5f, 1f,
            0.5f, 1f,
            0.5f, 0.5f,
            0f, 0.5f
        });
    }
//</editor-fold>

}
