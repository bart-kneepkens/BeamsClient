/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.objects;

import gui.lib.MouseActor;
import gui.lib.GUIElement;
import gui.lib.GUIParent;
import gui.lwjgl.GUIElementLoader;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import rx.Observable;
import gui.lib.MouseState;
import gui.lib.GUIRenderable;
import org.newdawn.slick.opengl.Texture;

/**
 * An instance of this class represents a button that can be clicked, pressed
 * and hovered over.
 *
 * @author Blackened
 */
public class Button extends MouseActor implements GUIRenderable {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private GUIParent parent;

    private final GUIElement guiElement;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    @Override
    public GUIElement getGUIElement() {
        return this.guiElement;
    }

    @Override
    public GUIParent getParent() {
        return this.parent;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new instance of the Button class.
     *
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param position The position of the (left bottom corner of the) button in
     * pixels.
     * @param rotation The Euler rotation of the button.
     */
    public Button(GUIParent parent, int width, int height, Vector2f position, int z_index, Vector3f rotation) {
        this.guiElement = new GUIElement(width, height, position, z_index, rotation);
        this.parent = parent;
    }

    public Button(GUIParent parent, int width, int height, Vector2f position, int z_index) {
        this.guiElement = new GUIElement(width, height, position, z_index);
        this.parent = parent;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        this.setMouseSubscription(inputObservable
                .subscribe(x -> {
                    if (this.guiElement.inRange(x)) {
                        if (x.isButton0Down() && this.guiElement.inRange(x.getPressOrigin())) {
                            this.changeToClickTexture();
                            this.setPressed(x, this, true);
                        } else {
                            if (this.isPressed()) {
                                this.click(x, this);
                                this.setPressed(x, this, false);
                            }
                            this.hover(x, this);
                            this.changeToHoverTexture();
                        }

                    } else {
                        this.changeToMainTexture();
                        this.setPressed(x, this, false);
                    }
                },
                        x -> x.printStackTrace()));
    }

    public void loadTextureAtlas(String name) throws IOException {
        Texture texture = GUIElementLoader.loadTexture(name);
        this.guiElement.setTexture(texture);

        float[] newTextureCoords = new float[]{
            0, 0,
            0, texture.getHeight(),
            texture.getWidth(), texture.getHeight(),
            texture.getWidth(), texture.getHeight(),
            texture.getWidth(), 0,
            0, 0
        };

        this.guiElement.setTextureCoords(newTextureCoords);
        this.changeToMainTexture();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Changes the buttons active texture to the hover texture.
     */
    private void changeToHoverTexture() {
        this.guiElement.loadTextureCoords(new float[]{
            this.guiElement.getTexture().getWidth() / 2, 0,
            this.guiElement.getTexture().getWidth() / 2, this.guiElement.getTexture().getHeight() / 2,
            this.guiElement.getTexture().getWidth(), this.guiElement.getTexture().getHeight() / 2,
            this.guiElement.getTexture().getWidth(), this.guiElement.getTexture().getHeight() / 2,
            this.guiElement.getTexture().getWidth(), 0,
            this.guiElement.getTexture().getWidth() / 2, 0
        });
    }

    /**
     * Changes the buttons active texture to the main texture.
     */
    private void changeToMainTexture() {
        this.guiElement.loadTextureCoords(new float[]{
            0, 0,
            0, this.guiElement.getTexture().getHeight() / 2,
            this.guiElement.getTexture().getWidth() / 2, this.guiElement.getTexture().getHeight() / 2,
            this.guiElement.getTexture().getWidth() / 2, this.guiElement.getTexture().getHeight() / 2,
            this.guiElement.getTexture().getWidth() / 2, 0,
            0, 0
        });
    }

    /**
     * Changes the buttons active texture to the click texture.
     */
    private void changeToClickTexture() {
        this.guiElement.loadTextureCoords(new float[]{
            0f, this.guiElement.getTexture().getHeight() / 2,
            0f, this.guiElement.getTexture().getHeight(),
            this.guiElement.getTexture().getWidth() / 2, this.guiElement.getTexture().getHeight(),
            this.guiElement.getTexture().getWidth() / 2, this.guiElement.getTexture().getHeight(),
            this.guiElement.getTexture().getWidth() / 2, this.guiElement.getTexture().getHeight() / 2,
            0f, this.guiElement.getTexture().getHeight() / 2
        });
    }
//</editor-fold>

}
