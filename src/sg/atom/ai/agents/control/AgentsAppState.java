package sg.atom.ai.agents.control;

import com.jme3.app.Application;
import sg.atom.corex.spatial.PhysicalSpatialControl;
import sg.atom.ai.agents.Agent;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.LinkedList;
import java.util.List;
import sg.atom.core.AtomMain;

/**
 * Class with information about agents and consequences of their behaviours in
 * game. It is not necessary to use it but it enables easier game status
 * updates. Contains agents and gameObjects and provides generic game control.
 *
 * @author Tihomir Radosavljević
 * @version 1.2
 */
public class AgentsAppState extends AbstractAppState {

    protected AtomMain app;
    protected AgentsAbstractControl gameControl;
    protected List<Agent> agents;
    protected List<PhysicalSpatialControl> gameObjects;
    protected boolean over = false;
    protected boolean friendlyFire = true;

    private static class GameHolder {

        private static final AgentsAppState INSTANCE = new AgentsAppState();
    }

    public static AgentsAppState getInstance() {
        return GameHolder.INSTANCE;
    }

    protected AgentsAppState() {
        agents = new LinkedList<Agent>();
        gameObjects = new LinkedList<PhysicalSpatialControl>();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setApp((AtomMain) app);
    }

    /**
     * Adding agent to game. It will be automaticly updated when game is
     * updated, and agent's position will be one set into Spatial.
     *
     * @param agent agent which is added to game
     */
    public void addAgent(Agent agent) {
        agent.setEnabled(true);
        agents.add(agent);
    }

    /**
     * Adding agent to game. It will be automaticly updated when game is
     * updated.
     *
     * @param agent agent which is added to game
     * @param position position where spatial should be added
     */
    public void addAgent(Agent agent, Vector3f position) {
        agent.setLocalTranslation(position);
        agent.setEnabled(true);
        agents.add(agent);
    }

    /**
     * Adding agent to game. It will be automaticly updated when game is
     * updated.
     *
     * @param agent agent which is added to game
     * @param x X coordinate where spatial should be added
     * @param y Y coordinate where spatial should be added
     * @param z Z coordinate where spatial should be added
     */
    public void addAgent(Agent agent, float x, float y, float z) {
        agent.setLocalTranslation(x, y, z);
        agent.setEnabled(true);
        agents.add(agent);
    }

    /**
     * Removing agent from list of agents to be updated and its spatial from
     * game.
     *
     * @param agent agent who should be removed
     */
    public void removeAgent(Agent agent) {
        agent.setEnabled(false);
        agents.remove(agent);
    }

    /**
     * Disabling agent. It means from agent will be dead and won't updated.
     *
     * @param agent
     */
    public void disableAgent(Agent agent) {
        agent.setEnabled(false);
    }

    /**
     * Check what agents are seen by one agent.
     *
     * @param agent agent which is looking
     * @param viewAngle
     * @return all agents that is seen by agent
     */
    public List<PhysicalSpatialControl> look(Agent agent, float viewAngle) {
        List<PhysicalSpatialControl> temp = new LinkedList<PhysicalSpatialControl>();
        //are there seen agents
        for (int i = 0; i < agents.size(); i++) {
            if (agents.get(i).isEnabled()) {
                if (!agents.get(i).equals(agent) && checkLookable(agent, agents.get(i), viewAngle)) {
                    temp.add(agents.get(i));
                }
            }
        }
        for (PhysicalSpatialControl gameObject : gameObjects) {
            if (gameObject.isEnabled() && checkLookable(agent, gameObject, viewAngle)) {
                temp.add(gameObject);
            }
        }
        return temp;
    }

    /**
     * Use with cautious. It works for this example, but it is not general it
     * doesn't include obstacles into calculation.
     *
     * @param observer
     * @param gameObject
     * @param heightAngle
     * @param widthAngle
     * @return
     */
    public boolean checkLookable(Agent observer, PhysicalSpatialControl gameObject, float viewAngle) {
        //if agent is not in visible range
        if (observer.getLocalTranslation().distance(gameObject.getLocalTranslation())
                > observer.getVisibilityRange()) {
            return false;
        }
        Vector3f direction = observer.getLocalRotation().mult(new Vector3f(0, 0, -1));
        Vector3f direction2 = observer.getLocalTranslation().subtract(gameObject.getLocalTranslation()).normalizeLocal();
        float angle = direction.angleBetween(direction2);
        if (angle > viewAngle) {
            return false;
        }
        return true;
    }

    @Override
    public void update(float tpf) {
        for (int i = 0; i < agents.size(); i++) {
            agents.get(i).update(tpf);
        }
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update(tpf);
        }
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public List<PhysicalSpatialControl> getGameObjects() {
        return gameObjects;
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public void addGameObject(PhysicalSpatialControl gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(PhysicalSpatialControl gameObject) {
        gameObjects.remove(gameObject);
    }

    public AgentsAbstractControl getGameControl() {
        return gameControl;
    }

    public void setGameControl(AgentsAbstractControl gameControl) {
        this.gameControl = gameControl;
    }

    public void attachSpatial(Spatial spatial) {
        getApp().getWorldManager().putSpatial(spatial, Vector2f.ZERO);
    }

    public void start() {
        for (Agent agent : agents) {
            agent.start();
        }
    }

    public AtomMain getApp() {
        return app;
    }

    public void setApp(AtomMain app) {
        this.app = app;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isEnemy(Agent agent1, Agent agent2) {
        return agent1.getTeam() != agent2.getTeam();
    }
}
