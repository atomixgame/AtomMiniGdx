/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ServiceManager;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import java.util.concurrent.Executors;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.io.text.TextLoader;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.assets.sprite.SpriteSheetLoader;
import sg.atom.corex.config.DeviceInfo;
import sg.atom.corex.entity.EntityManager;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.assets.GlobalAssetCache;
import sg.atom.corex.managers.EffectManager;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.managers.MaterialManager;
import sg.atom.corex.managers.SoundManager;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.managers.WorldManager;

/**
 *
 * @author cuong.nguyen
 */
public class AtomMain extends SimpleApplication implements IGameCycle {

    protected StageManager stageManager;
    protected GUIManager guiManager;
    protected AbstractManager gamePlayManager;
    protected WorldManager worldManager;
    protected SoundManager soundManager;
    protected MaterialManager materialManager;
    protected EffectManager effectManager;
    protected EntityManager entityManager;
//    protected NetworkManager networkManager;
//    protected GameStateManager gameStateManager;
    protected DeviceInfo deviceInfo;
    protected boolean customCycle = false;
    protected ServiceManager serviceManager;
    protected ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    protected GlobalAssetCache globalAssetCache;

    @Override
    public void simpleInitApp() {
        init();
        load();
    }

    public void init() {
        initConfigurations();
        initServices();
        initAssets();
        initManagers();
        activeManagers();
        initStates();
    }

    protected void initAssets() {
        this.assetManager.registerLoader(SpriteSheetLoader.class, "sprites");
        this.assetManager.registerLoader(TextLoader.class, "json");
        this.globalAssetCache = new GlobalAssetCache();
        this.stateManager.attach(this.globalAssetCache);
        this.globalAssetCache.initialize(stateManager, this);
    }

    protected void initManagers() {

        this.entityManager = new EntityManager(this);
        this.materialManager = new MaterialManager(this);
        this.soundManager = new SoundManager(this);
        this.worldManager = new WorldManager(this);
        this.stageManager = new StageManager(this);
        this.guiManager = new GUIManager(this);
        this.effectManager = new EffectManager(this);

        globalAssetCache.init();
        entityManager.init();
        materialManager.init();
        soundManager.init();
        stageManager.init();
        worldManager.init();
        guiManager.init();
        effectManager.init();
    }

    protected void activeManagers() {
        entityManager.getEventBus().register(worldManager);
    }

    protected void initServices() {
    }

    protected void initStates() {
    }

    protected void initConfigurations() {
    }

    public void load() {
        globalAssetCache.load();
        materialManager.load();
        soundManager.load();
        guiManager.load();
    }

    public void config(Configuration props) {
    }

    public void update(float tpf) {
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    public void finish() {
        globalAssetCache.finish();
        guiManager.finish();
        stageManager.finish();
        worldManager.finish();
        gamePlayManager.finish();
        soundManager.finish();
        materialManager.finish();
    }

    @Override
    public void destroy() {
        finish();
        super.destroy();
    }

    public AppSettings getSettings() {
        return settings;
    }

    public MaterialManager getMaterialManager() {
        return materialManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setCustomCycle(boolean customCycle) {
        this.customCycle = customCycle;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public ListeningExecutorService getExecutorService() {
        return executorService;
    }

    public GlobalAssetCache getGlobalAssetCache() {
        return globalAssetCache;
    }

    public AbstractManager getGamePlayManager() {
        return gamePlayManager;
    }

    public boolean isDebugMode() {
        return false;
    }

}
