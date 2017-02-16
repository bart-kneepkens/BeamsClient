/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity;

import beamsClient.BeamsClient;
import static beamsClient.BeamsClient.*;
import dataAccess.OBJLoader;
import dataAccess.lwjgl.Loader;
import game.entity.models.ModelTexture;
import game.entity.models.RawModel;
import game.entity.models.TexturedModel;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 *
 * @author Blackened
 */
public class Wizard extends Player {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private Map<String, TexturedModel> spellModels;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Wizard(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
        spellModels = Wizard.getSpellModels();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public void castSpell1() {
        System.out.println("Fired bullet!");
        if (super.getActiveSpell() == null) {
            LightSpell bulletSpell = new Bullet("bullet " + DisplayManager.getCurrentTime(),
                    DisplayManager.getCurrentTime(),
                    1000,
                    new Vector3f((float) Math.sin(this.getRotation().getY()) * 2,
                            0,
                            (float) Math.cos(this.getRotation().getY()) * 2),
                    this.spellModels.get("bullet"),
                    new Vector3f(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ()),
                    new Vector3f(this.getRotation().getX(), this.getRotation().getY(), this.getRotation().getZ()),
                    0.2f);
            BeamsClient.getInstance().getScene().addEntity(bulletSpell);
            BeamsClient.getInstance().getScene().getLights().add(bulletSpell.getLight());

            super.setActiveSpell(bulletSpell);
            LightSpell.LAST_ONE_FIRED = DisplayManager.getCurrentTime();
        }
    }

    @Override
    public void castSpell2() {
        if (super.getActiveSpell() == null) {
            LightSpell haloSpell = new Halo("halo" + DisplayManager.getCurrentTime(),
                    DisplayManager.getCurrentTime(),
                    2000,
                    this.spellModels.get("halo"),
                    new Vector3f(this.getPosition().getX(), this.getPosition().getY() + 1, this.getPosition().getZ()),
                    new Vector3f(0, 0, 0));
            BeamsClient.getInstance().getScene().getLights().add(haloSpell.getLight());
            super.setActiveSpell(haloSpell);
            LightSpell.LAST_ONE_FIRED = DisplayManager.getCurrentTime();
        }
    }

    @Override
    public void castSpell3() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void castSpell4() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void castSpell5() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void castSpell6() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void castSpell7() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Static Methods">
    /**
     *
     * @return
     */
    public static Map<String, TexturedModel> getSpellModels() {
        HashMap<String, TexturedModel> spellMap = new HashMap<>();

        // Loads the default bullet textured model.
        RawModel bulletModel = OBJLoader.loadObjModel(DEFAULT_BULLET_MODEL);
        ModelTexture bulletTexture = new ModelTexture(Loader.loadTexture(DEFAULT_BULLET_TEXTURE));
        TexturedModel texturedBulletModel = new TexturedModel(bulletModel, bulletTexture);
        bulletTexture.setReflectivity(1);
        bulletTexture.setShineDamper(10);

        spellMap.put("bullet", texturedBulletModel);

        return spellMap;
    }

    //</editor-fold>
}
