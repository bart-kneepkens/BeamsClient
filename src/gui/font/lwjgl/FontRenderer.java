package gui.font.lwjgl;

import gui.font.fontMeshCreator.GUIText;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import static toolbox.AttributeListPosition.*;

public class FontRenderer extends FontShader {

    public void renderText(GUIText text) {
        GL30.glBindVertexArray(text.getMesh());
        GL20.glEnableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glEnableVertexAttribArray(TEXTURE_COORDS);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, text.getFont().getTextureAtlas());
        super.loadColour(text.getColour());
        super.loadTranslation(new Vector2f(text.getPosition().getX() / Display.getWidth(), text.getPosition().getY() / Display.getHeight()));
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
        GL20.glDisableVertexAttribArray(VERTEX_POSITIONS);
        GL20.glDisableVertexAttribArray(TEXTURE_COORDS);
        GL30.glBindVertexArray(0);

    }

}
