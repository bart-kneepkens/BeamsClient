/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.font.lwjgl;

import dataAccess.lwjgl.VAO_Loader;
import gui.font.fontMeshCreator.FontType;
import gui.font.fontMeshCreator.GUIText;
import gui.font.fontMeshCreator.TextMeshData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Blackened
 */
public class TextMaster {
    
    private static Map<FontType, List<GUIText>> texts = new HashMap<>();
    private static FontRenderer renderer = new FontRenderer();
    
    
    public static void render(){
//        renderer.render(texts);
    }
    
    public static void loadText(GUIText text){
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int vao = VAO_Loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = texts.get(font);
        if(textBatch == null){
            textBatch = new ArrayList<>();
            texts.put(font, textBatch);
        }
        textBatch.add(text);
    }
    
    public static void removeText(GUIText text){
        List<GUIText> textBatch = texts.get(text.getFont());
        textBatch.remove(text);
        if(textBatch.isEmpty()){
            texts.remove(text.getFont());
        }
    }
    
    public static void cleanUp(){
        renderer.cleanUp();
    }
    
}
