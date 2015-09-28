package sg.atom.ai.agents;

import sg.atom.ai.behaviours.Behaviour;
import sg.atom.ai.behaviours.npc.SimpleMainBehaviour;
import com.jme3.scene.Spatial;
import sg.atom.corex.spatial.PhysicalSpatialControl;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import java.util.HashSet;
import java.util.Set;
import sg.atom.ai.agents.control.AgentsAppState;
import sg.atom.core.AtomMain;

/**
 * Class that represents Agent. Note: Not recommended for extending. Use
 * generics.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class Agent<T> extends PhysicalSpatialControl {

    /**
     * Class that enables you to add all variable you need for your agent.
     */
    private T model;
    /**
     * Unique name of Agent.
     */
    private String name;
    /**
     * Name of team. Primarily used for enabling friendly fire.
     */
    private AgentTeam team;
    /**
     * Main behaviour of Agent. Behaviour that will be active while his alive.
     */
    private Behaviour mainBehaviour;
    /**
     * Visibility range. How far agent can see.
     */
    private float visibilityRange;
    /**
     * Camera that is attached to agent.
     */
    private Camera camera;

    private Set<Behaviour> behaviours;
    private AgentsAppState agentAppState;

    /**
     * @param name unique name/id of agent
     */
    public Agent(String name) {
        this(name, null);
    }

    /**
     * @param name unique name/id of agent
     * @param spatial spatial that will agent have durring game
     */
    public Agent(String name, Spatial spatial) {
        this.name = name;
        this.spatial = spatial;
        this.behaviours = new HashSet<Behaviour>();
    }

    @Override
    public void init(AtomMain app) {
        super.init(app);

        this.agentAppState = app.getStateManager().getState(AgentsAppState.class);
    }

    public <T extends Behaviour> T getBehaviour(Class<T> clazz) {
        for (Behaviour b : behaviours) {
            if (clazz.isInstance(b)) {
                return (T) b;
            }
        }
        return null;
    }

    public void addBehaviour(Behaviour behaviour) {
        this.behaviours.add(behaviour);
    }

    /**
     * @return main behaviour of agent
     */
    public Behaviour getMainBehaviour() {
        return mainBehaviour;
    }

    /**
     * Setting main behaviour to agent. For more how should main behaviour look
     * like:
     *
     * @see SimpleMainBehaviour
     * @param mainBehaviour
     */
    public void setMainBehaviour(Behaviour mainBehaviour) {
        this.mainBehaviour = mainBehaviour;
        this.mainBehaviour.setEnabled(false);
    }

    public Set<Behaviour> getBehaviours() {
        return behaviours;
    }

    /**
     * @return unique name/id of agent
     */
    public String getName() {
        return name;
    }

    /**
     * Method for starting agent. Note: Agent must be alive to be started.
     *
     * @see Agent#enabled
     */
    public void start() {
        enabled = true;
        mainBehaviour.setEnabled(true);
    }

    /**
     * @return visibility range of agent
     */
    public float getVisibilityRange() {
        return visibilityRange;
    }

    /**
     * @param visibilityRange how far agent can see
     */
    public void setVisibilityRange(float visibilityRange) {
        this.visibilityRange = visibilityRange;
    }

    /**
     * @return model of agent
     */
    public T getModel() {
        return model;
    }

    /**
     * @param model of agent
     */
    public void setModel(T model) {
        this.model = model;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.model != null ? this.model.hashCode() : 0);
        hash = 47 * hash + (this.team != null ? this.team.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Agent<T> other = (Agent<T>) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.team != other.team && (this.team == null || !this.team.equals(other.team))) {
            return false;
        }
        return true;
    }

    @Override
    protected void controlUpdate(float tpf) {
//        System.out.println("Update agent!" + name);
        if (mainBehaviour != null) {
//            System.out.println("Update agent mainBehaviour!" + name);
            mainBehaviour.update(tpf);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("You should override it youself");
    }

    /**
     * @return team in which agent belongs
     */
    public AgentTeam getTeam() {
        return team;
    }

    /**
     * @param team in which agent belongs
     */
    public void setTeam(AgentTeam team) {
        this.team = team;
        if (!team.getMembers().contains(this)) {
            team.addMember(this);
        }
    }

    /**
     * Check if this agent is in same team as another agent.
     *
     * @param agent
     * @return true if they are in same team, false otherwise
     */
    public boolean isSameTeam(Agent agent) {
        if (team == null || agent.getTeam() == null) {
            return false;
        }
        return team.equals(agent.getTeam());
    }

    /**
     * @return camera that is attached to agent
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Setting camera for agent. It is recommended for use mouse input.
     *
     * @param camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public AgentsAppState getAgentAppState() {
        return agentAppState;
    }
}
