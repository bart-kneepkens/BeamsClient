/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderEngine;

/**
 *
 * @author Blackened
 */
public enum AttributeListName {
    
    VERTEX_POSITIONS(0),
    TEXTURE_COORDS(1);
    
    private int numVal;

    AttributeListName(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
    
}
