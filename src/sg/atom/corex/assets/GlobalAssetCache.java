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

    public HashMap<String, String> modelMap;
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

    public GlobalAssetCache() {
        modelMap = new HashMap<String, String>();
        spriteSheets = new HashMap<String, SpriteSheet>();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    public void load() {
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
}
