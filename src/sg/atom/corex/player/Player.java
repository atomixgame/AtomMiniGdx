package sg.atom.corex.player;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import java.util.HashMap;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;

/**
 *
 * @author cuongnguyen
 */
public class Player implements ManagableObject {

    protected AtomMain app;
    protected int id;
    protected String uid;
    protected String name;
    protected PlayerInfo playerInfo;
    protected HashMap<String, Object> userData;
    protected boolean isBot = false;

    public Player(AtomMain app, String name) {
        this.app = app;
        this.name = name;
        this.uid = name;
    }

    public Player(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public Player(String name) {
        this.name = name;
        this.uid = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void init(Application app) {
        this.app = (AtomMain) app;
        this.userData = new HashMap<String, Object>();
        if (this.playerInfo == null) {
            playerInfo = new PlayerInfo(id, name, null);
        }
    }

    public void initManagers(IGameCycle... managers) {
    }

    public void load(AssetManager assetManager) {
    }

    public void config(Configuration configuration) {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    public AtomMain getApp() {
        return app;
    }

    public HashMap<String, Object> getUserData() {
        return userData;
    }

    public boolean isBot() {
        return isBot;
    }
}
