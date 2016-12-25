/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.lib.GUIElement;
import GUI.lib.MouseActor;
import GUI.lwjgl.GUIElementLoader;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import rx.Observable;
import userInput.MouseState;
import GUI.lib.GUIRenderable;
import dataAccess.lwjgl.Loader;
import fontMeshCreator.FontType;
import java.io.File;

/**
 * TODO
 *
 * @author Blackened
 */
public class Checkbox extends MouseActor implements GUIRenderable {

    private boolean checked = false;

    private final GUIElement guiElement;

    public Checkbox(int width, int height, Vector2f position, int z_index) throws IOException {
        this.guiElement = new GUIElement(width, height, position, z_index);
        this.loadTextureAtlas("buttons/ckbox");
        
        FontType font = new FontType(Loader.loadTexture(new File("res/fonts/arial.png")), new File("res/fonts/arial.fnt"));

        Label label = new Label("hello does this wosdafdddasdfadsfsadfasdfasdfsdafsadfsadfsadfsdfrk?", 1f, font, new Vector2f(position.getX(), position.getY()), 1, false);
        label.setColour(1, 1, 1);
        this.guiElement.setLabel(label);
    }

    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        this.mouseSubscription = inputObservable
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
                        x -> x.printStackTrace());
    }

    @Override
    public GUIElement getGUIElement() {
        return guiElement;
    }

    public boolean isChecked() {
        return checked;
    }

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

    public final void loadTextureAtlas(String name) throws IOException {
        this.guiElement.setTextureID(GUIElementLoader.loadTexture(name));
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
}
