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
import java.io.IOException;
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

    private int textureID;

    private final GUIElement guiElement;

    /**
     * Creates a new instance of the Button class.
     *
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param position The position of the (left bottom corner of the) button in
     * pixels.
     * @param rotation The Euler rotation of the button.
     */
    public Button(int width, int height, Vector2f position, int z_index, Vector3f rotation) {
        this.guiElement = new GUIElement(width, height, position, z_index,rotation);
    }

    public Button(int width, int height, Vector2f position, int z_index) {
        this.guiElement = new GUIElement(width, height, position, z_index);
    }

    /**
     * Changes the buttons active texture to the hover texture.
     */
    private void changeToHoverTexture() {
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
     * Changes the buttons active texture to the main texture.
     */
    private void changeToMainTexture() {
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
     * Changes the buttons active texture to the click texture.
     */
    private void changeToClickTexture() {
        this.guiElement.loadTextureCoords(new float[]{
            0f, 0.5f,
            0f, 1f,
            0.5f, 1f,
            0.5f, 1f,
            0.5f, 0.5f,
            0f, 0.5f
        });
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

    public void loadTextureAtlas(String name) throws IOException {
        this.guiElement.setTextureID(GUIElementLoader.loadTexture(name));
        this.changeToMainTexture();
    }

    public int getTextureID() {
        return textureID;
    }

    @Override
    public GUIElement getGUIElement() {
        return this.guiElement;
    }

}
