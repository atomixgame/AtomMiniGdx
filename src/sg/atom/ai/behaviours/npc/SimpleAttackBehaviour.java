package sg.atom.ai.behaviours.npc;

import sg.atom.ai.agents.Agent;
import sg.atom.ai.behaviours.Behaviour;
import sg.atom.ai.agents.events.GameObjectSeenEvent;
import sg.atom.ai.agents.events.GameObjectSeenListener;
import sg.atom.corex.spatial.PhysicalSpatialControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;

/**
 * Simple attack behaviour for NPC. This behaviour is for to agent to attack
 * targetedObject or fixed point in space. It will attack with weapon, and it
 * doesn't check if it has ammo.
 *
 * @see AbstractWeapon#hasBullets()
 * @see AbstractWeapon#isInRange(com.jme3.math.Vector3f)
 * @see AbstractWeapon#isInRange(com.jme3.ai.agents.util.GameObject)
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class SimpleAttackBehaviour extends Behaviour implements GameObjectSeenListener {

    /**
     * Target for attack behaviour.
     */
    protected PhysicalSpatialControl targetedObject;
    /**
     * Target for attack behaviour.
     */
    protected Vector3f targetPosition;

    /**
     * @param agent to whom behaviour belongs
     */
    public SimpleAttackBehaviour(Agent agent) {
        super(agent);
    }

    /**
     * @param agent to whom behaviour belongs
     * @param spatial active spatial durring this behaviour
     */
    public SimpleAttackBehaviour(Agent agent, Spatial spatial) {
        super(agent, spatial);
    }

    /**
     * @param target at which agent will attack
     */
    public void setTarget(PhysicalSpatialControl target) {
        this.targetedObject = target;
    }

    /**
     *
     * @param target at what point will agent attack
     */
    public void setTarget(Vector3f target) {
        this.targetPosition = target;
    }

    @Override
    protected void controlUpdate(float tpf) {
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    /**
     * Behaviour can automaticaly update its targetedObject with
     * GameObjectSeenEvent, if it is agent, then it check if it is in range and
     * check if they are in same team.
     *
     * @param event
     */
    public void handleGameObjectSeenEvent(GameObjectSeenEvent event) {
        if (event.getGameObjectSeen() instanceof Agent) {
            Agent targetAgent = (Agent) event.getGameObjectSeen();
            if (agent.isSameTeam(targetAgent)) {
                return;
            }
        }
        targetedObject = event.getGameObjectSeen();
        enabled = true;
    }

    protected boolean canAttack() {
        return isTargetSet();
    }

    protected void afterAttack() {
        targetPosition = null;
        targetedObject = null;
    }

    /**
     * Method for checking is there any target that agent should attack.
     *
     * @return true if target is set
     */
    public boolean isTargetSet() {
        return targetPosition != null || targetedObject != null;
    }
}
