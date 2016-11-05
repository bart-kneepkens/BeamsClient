/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import java.util.List;

/**
 * THIS CLASS IS IMMUTABLE!
 * @author Blackened
 */
public class MouseState {
    
    private boolean button0Down;
    private boolean button1Down;
    
    private int X;
    private int Y;

    public MouseState(boolean button0Down, boolean button1Down, int X, int Y) {
        this.button0Down = button0Down;
        this.button1Down = button1Down;
        this.X = X;
        this.Y = Y;
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

    public MouseState button0Down(boolean value) {
        return new MouseState(value, this.button1Down, this.X, this.Y);
    }

    public MouseState button1Down(boolean value) {
        return new MouseState(this.button0Down, value, this.X, this.Y);
    }

    public MouseState X(int value) {
        return new MouseState(this.button0Down, this.button1Down, value, this.Y);
    }

    public MouseState Y(int value) {
        return new MouseState(this.button0Down, this.button1Down, this.X, value);
    }
    
    public boolean equals(MouseState other){
        return this.isButton0Down() == other.isButton0Down()
                && this.isButton1Down() == other.isButton1Down()
                && this.getX() == other.getX()
                && this.getY() == other.getY();
    }
    
    public boolean anyMatch(List<Input> input){
        if(input.contains(Input.MOUSE_BUTTON_0) && this.button0Down){
            return true;
        }
        return input.contains(Input.MOUSE_BUTTON_1) && this.button1Down;
    }
}
