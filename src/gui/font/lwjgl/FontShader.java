package gui.font.lwjgl;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.ShaderProgram;
import static toolbox.AttributeListPosition.TEXTURE_COORDS;
import static toolbox.AttributeListPosition.VERTEX_POSITIONS;

public class FontShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/GUI/font/lwjgl/fontVertex.glsl";
    private static final String FRAGMENT_FILE = "src/GUI/font/lwjgl/fontFragment.glsl";

    public FontShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        super.getUniformLocations().put("location_colour", super.getUniformLocation("colour"));
        super.getUniformLocations().put("location_translation", super.getUniformLocation("translation"));
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS, "position");
        super.bindAttribute(TEXTURE_COORDS, "textureCoords");
    }

    protected void loadColour(Vector3f colour) {
        super.loadVector(super.getUniformLocations().get("location_colour"), colour);
    }

    protected void loadTranslation(Vector2f translation) {
        super.load2DVector(super.getUniformLocations().get("location_translation"), translation);
    }

}
