/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import java.util.Set;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.player.Player;

/**
 *
 * @author cuongnguyen
 */
public class PlayerManager extends AbstractManager {

    Player localPlayer;
    Set<Player> players;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshPlayerData() {
    }

    public void refreshPlayerInfo() {
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public void askForLogin() {
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
