/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.logic.trigger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.Service;
import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 *
 * @author CuongNguyen
 */
public class TriggerManager extends AbstractAppState {
    // You can also write an executor instead of update loop.

    Service service;
    EventBus eventBus;
    // A Map or a Class your choice!
    BiMap<Spatial, Trigger> spatialTriggers;
    Multimap<Trigger, Predicate> predicates;
    ArrayList<Trigger> triggers;

    public void update(float tpf) {
        for (Trigger trigger : triggers) {
//            if (Predicates.from(predicates.get(trigger)).apply(spatialTriggers.get(trigger)) {
//                trigger.active();
//            }
        }
    }
    
//    public void 
    
    
}
