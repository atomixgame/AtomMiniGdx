/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.assets;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetKey;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapFont;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.execution.BaseGameState;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.assets.sprite.SpriteSheet;
import sg.atom.corex.config.DeviceInfo;
import sg.atom.corex.managers.WorldManager;

/**
 * High-level AssetCache singleton
 *
 * @author cuongnguyen
 */
public class GlobalAssetCache extends BaseGameState implements IGameCycle {

    HashMap<String, String> modelMap = new HashMap<String, String>();
    LoadingCache<String, Spatial> modelCache = CacheBuilder.newBuilder()
            .maximumSize(20)
            .expireAfterWrite(4, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Spatial>() {
                public Spatial load(String modelName) {
                    return loadModel(modelName);
                }
            });
    public HashMap<String, SpriteSheet> spriteSheets;
    public HashMap<String, BitmapFont> fonts;
    public HashMap<String, AudioNode> sounds;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    public void load() {
        createSpriteSheetMap();
        createModelMap();
    }

    public void init() {
    }

    public void config(Configuration props) {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    public HashMap<String, SpriteSheet> getSpriteSheets() {
        return spriteSheets;
    }

    public void addSpriteSheet(SpriteSheet sheet) {
        spriteSheets.put(sheet.getSimpleName(), sheet);
    }

    public void addSpriteSheet(String name, SpriteSheet sheet) {
        spriteSheets.put(name, sheet);
    }

    public Spatial getModel(String modelName) {
        if (!DeviceInfo.isAndroid()) {
            try {
                return modelCache.get(modelName);
            } catch (ExecutionException ex) {
                java.util.logging.Logger.getLogger(WorldManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return loadModel(modelName);
        }
        return new Node("EmptyNode");
    }

    protected Spatial loadModel(String modelName) {
        return app.getAssetManager().loadModel(modelMap.get(modelName));
    }

    protected void createModelMap() {
        modelMap.put("Warrior1", "Models/Troop/Warrior/Warrior1/Warrior1.j3o");
        modelMap.put("Warrior2", "Models/Troop/Warrior/Warrior2/Warrior2.j3o");
        modelMap.put("Warrior3", "Models/Troop/Warrior/Warrior3/Warrior3.j3o");
        modelMap.put("Tower", "Models/Architecture/Tower1.j3o");
        modelMap.put("Dragon", "Models/Dragon/dragon_fix.j3o");
        modelMap.put("Ogre", "Models/Troop/Warrior/Warrior1.j3o");
        modelMap.put("Castle1", "Models/Architecture/Castle2.j3o");
        modelMap.put("Flag", "Models/Architecture/Flag.j3o");
    }

    protected void createSpriteSheetMap() {
        spriteSheets = new HashMap<String, SpriteSheet>();
        spriteSheets.put("CommonIcons", assetManager.loadAsset(new AssetKey<SpriteSheet>("Interface/Images/CommonIcons.sprites")));
        spriteSheets.put("CommonElements", assetManager.loadAsset(new AssetKey<SpriteSheet>("Interface/Images/CommonElements.sprites")));
        spriteSheets.put("CharacterThumbnails", assetManager.loadAsset(new AssetKey<SpriteSheet>("Interface/Images/Characters/Thumbnails/Characters.sprites")));
        spriteSheets.put("ItemThumbnails", assetManager.loadAsset(new AssetKey<SpriteSheet>("Interface/Images/Items/Items.sprites")));
        spriteSheets.put("UiSkin1", assetManager.loadAsset(new AssetKey<SpriteSheet>("Interface/Skin/ui.sprites")));
        spriteSheets.put("UiSkin2", assetManager.loadAsset(new AssetKey<SpriteSheet>("Interface/Skin/ui2.sprites")));
    }
}
