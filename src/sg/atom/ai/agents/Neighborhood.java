/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.agents;

import java.util.Collection;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface Neighborhood {

    Collection<Agent> findNearby(Agent boid);

    double getRadius();
}
