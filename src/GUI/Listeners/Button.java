/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Listeners;

import GUI.GUIElement;
import GUI.GUIElementLoader;
import java.util.function.Consumer;
import org.lwjgl.util.vector.Vector2f;
import rx.Observable;
import rx.Subscription;
import userInput.Event;
import userInput.MouseInput;
import userInput.MouseState;

/**
 * An instance of this class represents a button that can be clicked, pressed
 * and hovered over.
 *
 * @author Blackened
 */
public class Button extends GUIElement implements IListener {

    /**
     * The name of the button.
     */
    private final String name;

    /**
     * Three different consumers, each for a different action.
     */
    private Consumer<Event> clickConsumer;
    private Consumer<Event> pressConsumer;
    private Consumer<Event> hoverConsumer;

    /**
     * Whether the button is down.
     */
    private boolean buttonDown = false;

    private int mainTextureID;
    private int hoverTextureID;
    private int clickTextureID;

    /**
     * The subscription to the mouse states, so that mouse position and button
     * states can be checked.
     */
    private Subscription mouseSubscription;

    /**
     * Creates a new instance of the Button class.
     *
     * @param name The name of the button.
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param position The position of the button in pixels.
     * @param rotation The Euler rotation of the button.
     */
    public Button(String name, int width, int height, Vector2f position, Vector2f rotation) {
        super(width, height, position, rotation);
        this.name = name;
        this.subscribe(MouseInput.getMouseSubject());
    }

    /**
     * Sets the click consumer for this instance. The consumer will be needed
     * when the button is clicked.
     *
     * @param consumer The consumer to set.
     */
    public void onClick(Consumer<Event> consumer) {
        this.clickConsumer = consumer;
    }

    /**
     * Sets the press consumer for this instance. The consumer will be needed
     * when the button is pressed.
     *
     * @param consumer The consumer to set.
     */
    public void onPress(Consumer<Event> consumer) {
        this.pressConsumer = consumer;
    }

    /**
     * Sets the hover consumer for this instance. The consumer will be needed
     * when the button is hovered over.
     *
     * @param consumer The consumer to set.
     */
    public void onHover(Consumer<Event> consumer) {
        this.hoverConsumer = consumer;
    }

    /**
     * Requests the click consumer to handle the buttons click event, if any.
     *
     * @param mouseState The state of the mouse when the button was clicked.
     */
    private void click(MouseState mouseState) {
        if (clickConsumer != null) {
            clickConsumer.accept(new Event(mouseState, this));
        }
    }

    /**
     * Requests the press consumer to handle the buttons press event, if any.
     *
     * @param mouseState The state of the mouse when the button was pressed.
     */
    private void press(MouseState mouseState) {
        if (pressConsumer != null) {
            pressConsumer.accept(new Event(mouseState, this));
        }
    }

    /**
     * Requests the hover consumer to handle the buttons hover event, if any.
     *
     * @param mouseState The state of the mouse when the button was hovered
     * over.
     */
    private void hover(MouseState mouseState) {
        if (hoverConsumer != null) {
            hoverConsumer.accept(new Event(mouseState, this));
        }
    }

    /**
     * Changes the buttons active texture to the hover texture.
     */
    private void changeToHoverTexture() {
        super.activeTextureID = this.hoverTextureID;
    }

    /**
     * Changes the buttons active texture to the main texture.
     */
    private void changeToMainTexture() {
        super.activeTextureID = this.mainTextureID;
    }

    /**
     * Changes the buttons active texture to the click texture.
     */
    private void changeToClickTexture() {
        super.activeTextureID = this.clickTextureID;
    }

    /**
     * Checks whether the mouse cursor is on the button.
     *
     * @param mouseState The current state of the mouse.
     * @return True if the mouse cursor is on the button, false otherwise.
     */
    private boolean inRange(MouseState mouseState) {
        if (mouseState.getX() > this.getPosition().x - (this.getWidth() / 2)
                && mouseState.getX() < this.getPosition().x + (this.getWidth() / 2)) {
            return mouseState.getY() > this.getPosition().y - (this.getHeight() / 2)
                    && mouseState.getY() < this.getPosition().y + (this.getHeight() / 2);
        }
        return false;
    }

    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        this.mouseSubscription = inputObservable
                .subscribe(x -> {
                    if (this.inRange(x)) {
                        if (x.isButton0Down()) {
                            this.changeToClickTexture();
                            this.press(x);
                            this.buttonDown = true;
                        } else {
                            if (this.buttonDown) {
                                this.click(x);
                                this.buttonDown = false;
                            }
                            this.hover(x);
                            this.changeToHoverTexture();
                        }

                    } else {
                        this.changeToMainTexture();
                        this.buttonDown = false;
                    }
                },
                        x -> System.out.println("OnError recieved!"));
    }

    @Override
    public void unsubscribeToUserInput() {
        this.mouseSubscription.unsubscribe();
    }

    public void loadMainTexture(String name) {
        this.mainTextureID = GUIElementLoader.loadTexture(name);
        this.activeTextureID = this.mainTextureID;
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

    public String getName() {
        return name;
    }

}
