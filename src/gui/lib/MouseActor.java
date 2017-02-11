/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.lib;

import java.util.function.Consumer;
import rx.Subscription;
import userInput.Event;
import userInput.MouseState;

/**
 *
 * @author Blackened
 */
public abstract class MouseActor implements IListener {

    private boolean pressed = false;

    /**
     * All consumers, each for a different mouse action.
     */
    private Consumer<Event> clickConsumer;
    private Consumer<Event> pressConsumer;
    private Consumer<Event> unPressConsumer;
    private Consumer<Event> hoverConsumer;
    private Consumer<Event> scrollConsumer;

    /**
     * The subscription to the mouse state.
     */
    protected Subscription mouseSubscription;

    /**
     * Getter for the pressed property.
     * @return True if actor is pressed, false otherwise.
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * 
     * @param mouseState
     * @param sender
     * @param value 
     */
    public void setPressed(MouseState mouseState, Object sender, boolean value) {
        if (value) {
            this.pressed = true;
            if (pressConsumer != null) {
                pressConsumer.accept(new Event(mouseState, sender));
            }
        }
        if (!value) {
            this.pressed = false;
            if (unPressConsumer != null) {
                unPressConsumer.accept(new Event(mouseState, sender));
            }
        }
    }

    /**
     * Sets the click consumer for this instance. The consumer will be needed
     * when the listener is clicked.
     *
     * @param consumer The consumer to set.
     */
    public final void onClick(Consumer<Event> consumer) {
        this.clickConsumer = consumer;
    }

    /**
     * Sets the press consumer for this instance. The consumer will be needed
     * when the listener is pressed.
     *
     * @param consumer The consumer to set.
     */
    public final void onPress(Consumer<Event> consumer) {
        this.pressConsumer = consumer;
    }

    public final void onUnPress(Consumer<Event> consumer) {
        this.unPressConsumer = consumer;
    }

    /**
     * Sets the hover consumer for this instance. The consumer will be needed
     * when the listener is hovered over.
     *
     * @param consumer The consumer to set.
     */
    public final void onHover(Consumer<Event> consumer) {
        this.hoverConsumer = consumer;
    }

    /**
     * Sets the scroll consumer for this instance. The consumer will be needed
     * when the listener is scrolled over.
     *
     * @param consumer The consumer to set.
     */
    public final void onScroll(Consumer<Event> consumer) {
        this.scrollConsumer = consumer;
    }

    /**
     * Requests the click consumer to handle the listeners click event, if any.
     *
     * @param mouseState The state of the mouse when the listener was clicked.
     * @param sender
     */
    protected final void click(MouseState mouseState, Object sender) {
        if (clickConsumer != null) {
            clickConsumer.accept(new Event(mouseState, sender));
        }
    }

    /**
     * Requests the hover consumer to handle the listeners hover event, if any.
     *
     * @param mouseState The state of the mouse when the listener was hovered
     * over.
     * @param sender
     */
    protected final void hover(MouseState mouseState, Object sender) {
        if (hoverConsumer != null) {
            hoverConsumer.accept(new Event(mouseState, sender));
        }
    }

    /**
     * Requests the scroll consumer to handle the listeners scroll event, if
     * any.
     *
     * @param mouseState The state of the mouse when the listener was scrolled
     * over.
     * @param sender
     */
    protected final void scroll(MouseState mouseState, Object sender) {
        if (scrollConsumer != null) {
            scrollConsumer.accept(new Event(mouseState, sender));
        }
    }

    @Override
    public final void unsubscribeToUserInput() {
        this.mouseSubscription.unsubscribe();
    }

}
