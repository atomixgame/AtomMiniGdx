/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage;

import com.jme3.app.Application;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import org.apache.log4j.Logger;
import org.customsoft.stateless4j.StateMachine;
import sg.atom.ai.AIEvent;
import sg.atom.ai.agents.Agent;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.corex.managers.StageManager;
import sg.atom.ai.fsm.AIState;

/**
 *
 * @author cuongnguyen
 */
public class BaseCharacter extends SpatialEntity implements GameActor {

    public static final Logger logger = Logger.getLogger(BaseCharacter.class);
    protected StateMachine<AIState, AIEvent> brain;
    protected Agent<BaseCharacter> agent;

    public BaseCharacter(long iid, String type, Spatial spatial) {
        super(iid, type, spatial);
    }

    @Override
    public void init(Application app) {
        super.init(app);
        this.agent = new Agent(type + getIid(), spatial);
        this.agent.init(this.app);
        this.agent.setModel(this);
    }

    @Override
    public void activate() {
        super.activate();
        this.agent.getMainBehaviour().setEnabled(true);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.agent.setEnabled(false);
        this.agent.getTeam().removeMember(agent);
    }

    public void resetState() {
    }

    public void onStage() {
    }

    public void onStage(StageManager stageManager) {
    }

    public void onOutStage(StageManager stageManager) {
    }

    public Agent<BaseCharacter> getAgent() {
        return agent;
    }

    public void setAgent(Agent<BaseCharacter> agent) {
        this.agent = agent;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        syncPos(tpf);
    }

    protected void syncPos(float tpf) {
        this.spatial.setLocalTranslation(agent.getLocalTranslation().clone());
    }

    public void setWalkDirection(Vector3f vel) {
        //        Vector3f newPos = getSpatial().getLocalTranslation().add(vel);
        //        getSpatial().setLocalTranslation(newPos);
    }

    public void setViewDirection(Vector3f normalize) {
    }

    public boolean isStaged() {
        return isActived;
    }

    public void act(int type, Object param) {
    }

    public void addAction(ActionTrigger actionTrigger, GameAction action) {
    }

}
