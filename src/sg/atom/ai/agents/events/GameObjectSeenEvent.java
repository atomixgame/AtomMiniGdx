package sg.atom.ai.agents.events;

import sg.atom.corex.spatial.PhysicalSpatialControl;

/**
 * Event for seen PhysicalObjects.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class GameObjectSeenEvent extends GameObjectEvent {

    /**
     * GameObject that have been seen.
     */
    private PhysicalSpatialControl gameObjectSeen;

    /**
     *
     * @param source object that produce this event (it is usually agent)
     * @param gameObject GameObject that have been seen
     */
    public GameObjectSeenEvent(Object source, PhysicalSpatialControl gameObject) {
        super(source);
        this.gameObjectSeen = gameObject;
    }

    /**
     *
     * @return seen GameObject
     */
    public PhysicalSpatialControl getGameObjectSeen() {
        return gameObjectSeen;
    }

    /**
     *
     * @param gameObjectSeen seen GameObject
     */
    public void setGameObjectSeen(PhysicalSpatialControl gameObjectSeen) {
        this.gameObjectSeen = gameObjectSeen;
    }
}
