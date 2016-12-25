/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import GUI.UserInterface;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.DisplayManager;
import toolbox.Autonomous;

/**
 *
 * @author Blackened
 */
public class MovingContainer extends Container implements Autonomous{
    
    private Vector2f restPosition;
    
    private Vector2f activePosition;
    
    private boolean active = false;
    
    public MovingContainer(int width, int height, Vector2f position, int z_index, UserInterface userInterface) {
        super(width, height, position, z_index, userInterface);
        this.activePosition = new Vector2f(position.getX(), position.getY());
        this.restPosition = new Vector2f(position.getX(), position.getY() - 100);
    }
    
    @Override
    public void update(){
        if(active){
            if (this.getGUIElement().getPosition().getY() < this.activePosition.getY()){
                this.getGUIElement().increasePosition(0,1 * DisplayManager.getFrameTimeSeconds());
            }
        }
        if(!active){
            if (this.getGUIElement().getPosition().getY() > this.restPosition.getY()){
                this.getGUIElement().increasePosition(0,-1 * DisplayManager.getFrameTimeSeconds());
            }
        }
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean value) {
        this.active = value;
    }
}
