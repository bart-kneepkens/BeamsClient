package fontRendering;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import static toolbox.AttributeListPosition.*;

public class FontRenderer extends FontShader {

    public void render(Map<FontType, List<GUIText>> texts) {
//            prepare();
//            for(FontType font : texts.keySet()){
//                GL13.glActiveTexture(GL13.GL_TEXTURE0);
//                GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
//                for(GUIText text : texts.get(font)){
//                    renderText(text);
//                }
//            }
//            endRendering();
    }

    public void renderText(GUIText text) {
        GL30.glBindVertexArray(text.getMesh());
        GL20.glEnableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glEnableVertexAttribArray(TEXTURE_COORDS);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, text.getFont().getTextureAtlas());
        super.loadColour(text.getColour());
        super.loadTranslation(new Vector2f(((2.0f * text.getPosition().x) / Display.getWidth()) - 1, ((2.0f * text.getPosition().y) / Display.getHeight()) - 1));
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
        GL20.glDisableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glDisableVertexAttribArray(TEXTURE_COORDS);
        GL30.glBindVertexArray(0);

    }

}
