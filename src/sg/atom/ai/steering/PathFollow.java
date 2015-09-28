package sg.atom.ai.steering;

import sg.atom.ai.agents.Agent;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import sg.atom.corex.stage.BaseCharacter;
import sg.atom.corex.world.road.Path;

/**
 * Simple move along path behaviour for NPC.
 */
public class PathFollow extends AbstractSteeringBehaviour {

    protected Path path;
    protected Vector3f startPos;
    /**
     * Targeted position.
     */
    protected Vector3f targetPosition;
    /**
     * Move direction of agent.
     */
    protected Vector3f moveDirection;
    /**
     * Distance of targetPosition that is acceptable.
     */
    protected float distanceError;

    public PathFollow(Agent agent, Path path) {
        super(agent);
        distanceError = 0.2f;
        this.path = path;
    }

    public PathFollow(Agent agent, Spatial spatial, Path path) {
        super(agent, spatial);
        distanceError = 0.2f;
        this.path = path;
    }

    public void onReachEndPath() {
        targetPosition = null;
        moveDirection = null;
        path = null;
//                    enabled = false;
    }

    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
//        model.setWalkDirection(this.velocity.mult(tpf));
    }

    @Override
    protected void rotateAgent(float tpf) {
        super.rotateAgent(tpf);
        getCharacter().setViewDirection(agent.getLocalRotation().mult(Vector3f.UNIT_Z).normalize());
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    /**
     * @return position of target
     */
    public Vector3f getTargetPosition() {
        return targetPosition;
    }

    /**
     * @param targetPosition position of target
     */
    public void setTargetPosition(Vector3f targetPosition) {
        this.targetPosition = targetPosition;
    }

    /**
     *
     * @return movement vector
     */
    public Vector3f getMoveDirection() {
        return moveDirection;
    }

    /**
     *
     * @return allowed distance error
     */
    public float getDistanceError() {
        return distanceError;
    }

    /**
     *
     * @param distanceError allowed distance error
     */
    public void setDistanceError(float distanceError) {
        this.distanceError = distanceError;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    protected Vector3f calculateSteering() {
        if (path == null) {
            return null;
        }
        if (startPos == null) {
            startPos = path.getStart();
            targetPosition = path.getPoints().get(1);
        }
        //if there is target position where agent should move

        if (targetPosition != null) {
            boolean reachedTargetPos = false;

            if (agent.getLocalTranslation().distance(targetPosition) <= distanceError) {
                reachedTargetPos = true;
            }

            if (reachedTargetPos) {
                if (targetPosition == path.getEnd()) {
                    onReachEndPath();
                } else {
                    startPos = path.nextPoint(startPos);
                    targetPosition = path.nextPoint(targetPosition);
                }

            } else {
                moveDirection = targetPosition.subtract(agent.getLocalTranslation()).normalize();
                moveDirection.multLocal(agent.getMoveSpeed());
            }
        } else {
            //Can not move because there is no target!
            moveDirection = Vector3f.ZERO;
        }
        return moveDirection;
    }

    @Override
    protected Vector3f calculateSteeredVelocity() {
        Vector3f steering = calculateSteering();
        if (steering != null) {
            if (steering.length() > agent.getMaxForce()) {
                steering = steering.normalize().mult(agent.getMaxForce());
            }
            agent.setAcceleration(steering.mult(1 / getAgentTotalMass()));
            velocity = velocity.add(agent.getAcceleration());
            if (velocity.length() > agent.getMaxMoveSpeed()) {
                velocity = velocity.normalize().mult(agent.getMaxMoveSpeed());
            }
            return velocity;
        } else {
            return Vector3f.ZERO;
        }
    }

    private BaseCharacter getCharacter() {
        return (BaseCharacter) getAgent().getModel();
    }
}
