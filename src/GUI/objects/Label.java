/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.objects;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Blackened
 */
public class Label extends GUIText{
    
    public Label(String text, float fontSize, FontType font, Vector2f position, float maxLineLength, boolean centered) {
        super(
                text, 
                fontSize, 
                font, 
                position, 
        maxLineLength, 
        centered);
    }
    
    
    
}
