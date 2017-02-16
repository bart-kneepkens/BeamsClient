/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.objects;

import gui.lib.MouseActor;
import org.lwjgl.util.vector.Vector2f;
import rx.Observable;
import gui.lib.MouseState;

/**
 *
 * @author Blackened
 */
public class InvisibleListener extends MouseActor {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private final int width;
    private final int height;
    private final Vector2f position;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new instance of the Panel class.
     *
     * @param width The width of the panel in pixels.
     * @param height The height of the panel in pixels.
     * @param position The position of the panel in pixels.
     */
    public InvisibleListener(int width, int height, Vector2f position) {
        this.width = width;
        this.height = height;
        this.position = position;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        this.setMouseSubscription(inputObservable
                .subscribe(x -> {
                    // Checks whether the cursor is in range of the panel.
                    if (this.inRange(x)) {
                        // Checks whether any of the two mouse buttons are down.
                        if (x.isButton0Down() || x.isButton1Down()) {
                            this.setPressed(x, this, true);
                        } else {
                            if (this.isPressed()) {
                                this.click(x, this);
                                this.setPressed(x, this, false);
                            }
                            this.hover(x, this);
                        }
                        // Checks whether the scrollwheel has been used
                        if (x.getScroll() != 0) {
                            this.scroll(x, this);
                        }
                    } else {
                        this.setPressed(x, this, false);
                    }
                },
                        x -> x.printStackTrace()));
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    private boolean inRange(MouseState mouseState) {
        if (mouseState.getX() > this.position.x
                && mouseState.getX() < this.position.x + this.width) {
            return mouseState.getY() > this.position.y
                    && mouseState.getY() < this.position.y + this.height;
        }
        return false;
    }
//</editor-fold>

}
