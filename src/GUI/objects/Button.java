/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.MouseActor;
import GUI.lib.Renderable;
import GUI.lib.GUIElement;
import GUI.lwjgl.GUIElementLoader;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import rx.Observable;
import userInput.MouseState;

/**
 * An instance of this class represents a button that can be clicked, pressed
 * and hovered over.
 *
 * @author Blackened
 */
public class Button extends MouseActor implements Renderable {

    private int mainTextureID;
    private int hoverTextureID;
    private int clickTextureID;

    private final GUIElement guiElement;

    /**
     * Creates a new instance of the Button class.
     *
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param position The position of the (left bottom corner of the) 
     * button in pixels.
     * @param rotation The Euler rotation of the button.
     */
    public Button(int width, int height, Vector2f position, Vector3f rotation) {
        this.guiElement = new GUIElement(width, height, position, rotation);
    }

    public Button(int width, int height, Vector2f position) {
        this.guiElement = new GUIElement(width, height, position);
    }

    /**
     * Changes the buttons active texture to the hover texture.
     */
    private void changeToHoverTexture() {
        this.guiElement.activeTextureID = this.hoverTextureID;
    }

    /**
     * Changes the buttons active texture to the main texture.
     */
    private void changeToMainTexture() {
        this.guiElement.activeTextureID = this.mainTextureID;
    }

    /**
     * Changes the buttons active texture to the click texture.
     */
    private void changeToClickTexture() {
        this.guiElement.activeTextureID = this.clickTextureID;
    }

    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        this.mouseSubscription = inputObservable
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
                        x -> x.printStackTrace());
    }

    public void loadMainTexture(String name) {
        this.mainTextureID = GUIElementLoader.loadTexture(name);
        this.guiElement.activeTextureID = this.mainTextureID;
    }

    public void loadHoverTexture(String name) {
        this.hoverTextureID = GUIElementLoader.loadTexture(name);
    }

    public void loadClickTexture(String name) {
        this.clickTextureID = GUIElementLoader.loadTexture(name);
    }

    public int getMainTextureID() {
        return mainTextureID;
    }

    public int getClickTextureID() {
        return clickTextureID;
    }

    public int getHoverTextureID() {
        return hoverTextureID;
    }

    @Override
    public GUIElement getGUIElement() {
        return this.guiElement;
    }

}
