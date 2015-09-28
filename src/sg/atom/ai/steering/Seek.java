package sg.atom.ai.steering;

import sg.atom.ai.movement.StaticAgent;
import sg.atom.ai.agents.Agent;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;

/**
 * Purpose of seek behaviour is to steer agent towards a specified position or
 * object. Basically it's movement behaviour.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class Seek extends AbstractSteeringBehaviour {

    /**
     * Agent whom we seek.
     */
    private Agent target;
    private Vector3f targetPos;

    /**
     * Constructor for seek behaviour.
     *
     * @param agent to whom behaviour belongs
     * @param target agent whom we seek
     */
    public Seek(Agent agent, Agent target) {
        super(agent);
        this.target = target;
    }

    /**
     * Constructor for seek behaviour.
     *
     * @param agent to whom behaviour belongs
     * @param target agent from we seek
     * @param spatial active spatial during excecution of behaviour
     */
    public Seek(Agent agent, Agent target, Spatial spatial) {
        super(agent, spatial);
        this.target = target;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    /**
     * Calculate steering vector.
     *
     * @return steering vector
     */
    protected Vector3f calculateSteering() {
        Vector3f desiredVelocity = target.getLocalTranslation().subtract(agent.getLocalTranslation()).normalize().mult(agent.getMoveSpeed());
        return desiredVelocity.subtract(velocity);
    }

    /**
     * Calculate new velocity for agent based on calculated steering behaviour.
     *
     * @return velocity vector
     */
    protected Vector3f calculateSteeredVelocity() {
        Vector3f steering = calculateSteering();
        if (steering.length() > agent.getMaxForce()) {
            steering = steering.normalize().mult(agent.getMaxForce());
        }
        agent.setAcceleration(steering.mult(1 / getAgentTotalMass()));
        velocity = velocity.add(agent.getAcceleration());
        if (velocity.length() > agent.getMaxMoveSpeed()) {
            velocity = velocity.normalize().mult(agent.getMaxMoveSpeed());
        }
        return velocity;
    }

    /**
     * Get agent from we seek.
     *
     * @return agent
     */
    public Agent getTarget() {
        return target;
    }

    /**
     * Setting agent from we seek.
     *
     * @param target
     */
    public void setTarget(Agent target) {
        this.target = target;
    }

    public void setTargetPos(Vector3f localTranslation) {
        this.target = new StaticAgent(localTranslation);
        this.target.setEnabled(true);
    }
}
