/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import org.lwjgl.util.vector.Vector2f;

/**
 * THIS CLASS IS IMMUTABLE!
 *
 * @author Blackened
 */
public class MouseState {

    private boolean button0Down;
    private boolean button1Down;
    private int scroll;

    private Vector2f pressOrigin;
    

    private int X;
    private int Y;

    public MouseState(boolean button0Down, boolean button1Down, int X, int Y, int scroll) {
        this.button0Down = button0Down;
        this.button1Down = button1Down;
        this.X = X;
        this.Y = Y;
        this.pressOrigin = null;
        this.scroll = scroll;
    }

    public MouseState(boolean button0Down, boolean button1Down, int X, int Y, int scroll, Vector2f pressOrigin) {
        this.button0Down = button0Down;
        this.button1Down = button1Down;
        this.X = X;
        this.Y = Y;
        this.pressOrigin = pressOrigin;
        this.scroll = scroll;
    }

    public Vector2f getPressOrigin() {
        return pressOrigin;
    }

    public MouseState pressOrigin(Vector2f pressOrigin) {
        return new MouseState(this.button0Down, this.button1Down, this.X, this.Y, this.scroll, pressOrigin);
    }

    public boolean isButton0Down() {
        return button0Down;
    }

    public boolean isButton1Down() {
        return button1Down;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getScroll() {
        return scroll;
    }

    public MouseState button0Down(boolean value) {
        return new MouseState(value, this.button1Down, this.X, this.Y, this.scroll, this.pressOrigin);
    }

    public MouseState button1Down(boolean value) {
        return new MouseState(this.button0Down, value, this.X, this.Y, this.scroll, this.pressOrigin);
    }

    public MouseState X(int value) {
        return new MouseState(this.button0Down, this.button1Down, value, this.Y, this.scroll, this.pressOrigin);
    }

    public MouseState Y(int value) {
        return new MouseState(this.button0Down, this.button1Down, this.X, value, this.scroll, this.pressOrigin);
    }

    public boolean equals(MouseState other) {
        return this.isButton0Down() == other.isButton0Down()
                && this.isButton1Down() == other.isButton1Down()
                && this.getX() == other.getX()
                && this.getY() == other.getY()
                && this.pressOrigin == other.pressOrigin
                && this.scroll == other.scroll;
    }
}
