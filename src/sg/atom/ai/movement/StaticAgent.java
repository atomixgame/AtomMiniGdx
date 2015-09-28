/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import sg.atom.ai.agents.Agent;

/**
 *
 * @author cuongnguyen
 */
public class StaticAgent extends Agent {

    public StaticAgent(Vector3f pos) {
        super(null);
        setSpatial(new Node());
        getSpatial().setLocalTranslation(pos);
    }
}
