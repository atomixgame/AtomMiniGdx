package sg.atom.corex.entity;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;
import sg.atom.corex.stage.GameAction;

/**
 * A Predefined Entity which assume it has an associated Spatial.
 *
 * SpatialEntity also contain a list(empty by default) of its action which is
 * contribute to the gameworld.
 *
 * This is the most "common" kind of Entity available in a "common" JME3 game!
 *
 * In this implementation I add fundamental ES support for this common
 * SceneGraph embeded Entity!
 * <ul>
 * <li>It can get an un-ordered list of its associated Component(s). Include
 * {@link SpatialInfo} as link to Spatial components </li>
 *
 * <li>It can get/set an ordered list of its associcated GameAction(s).</li>
 *
 * <li>It procedure BeanInfo.</li>
 *
 * <li>It proxy for its Spatial Controls.</li>
 *
 * </ul>
 *
 * @author atomix
 */
public class SpatialEntity implements ComposableEntity, ManagableObject {

    protected AtomMain app;
    protected static final long DEFAULT_NONE_ID = Long.MIN_VALUE;
    protected Entity entity;
    protected Spatial spatial;
    protected ArrayList<GameAction> actions = new ArrayList<GameAction>();
    protected long iid;
    protected String type;
    protected int[] functionIds;
    protected ArrayList<EntityFunction> functions = new ArrayList<EntityFunction>();
    protected int group;
    protected String tags;
    protected boolean isActived;
    protected boolean selectable = false;
    protected float boundingRadius = 0f;

    /**
     * Only use internal for factory initilization
     *
     */
    protected SpatialEntity() {
        this.iid = DEFAULT_NONE_ID;
    }

    public SpatialEntity(long iid) {
        this.iid = iid;
    }

    public SpatialEntity(long iid, String type) {
        this.iid = iid;
        this.type = type;
    }

    public SpatialEntity(long iid, String type, Spatial spatial) {
        this.iid = iid;
        this.type = type;
        this.spatial = spatial;
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public void setSpatial(Spatial model) {
        this.spatial = model;

    }

    public void activate() {
        isActived = true;
    }

    public void deactivate() {
        isActived = false;
    }

    public void addToStage(Node parentNode) {
        parentNode.attachChild(spatial);
    }

    public void removeFromStage() {
        this.spatial.removeFromParent();
    }

    public void addAction(GameAction action) {
        actions.add(action);
    }

    public void act(GameAction action) {

    }

    public void onEvent(Object event) {
    }

    public <T extends Control> T getControl(Class<T> controlType) {
        return spatial.getControl(controlType);
    }

    public long getIid() {
        return iid;
    }

    public EntityId getId() {
        return null;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void compose(Object... components) {
        for (Object component : components) {
            if (component instanceof Spatial) {
                this.setSpatial((Spatial) component);
            } else if (component instanceof Vector3f) {
                this.getSpatial().setLocalTranslation(((Vector3f) component).clone());
            } else if (component instanceof int[]) {
                this.functionIds = (int[]) component;
            }
        }

    }

    public void resetAttributes() {
    }

    public void persist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void init(Application app) {
        this.app = (AtomMain) app;
    }

    public void initManagers(IGameCycle... managers) {
    }

    public void load(AssetManager assetManager) {
    }

    public void config(Configuration configuration) {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    public static float distance(SpatialEntity spatialEntity, SpatialEntity target) {
        return spatialEntity.getSpatial().getLocalTranslation().distance(target.getSpatial().getLocalTranslation());
    }

    public ArrayList<EntityFunction> getFunctions() {
        return functions;
    }

    public int[] getFunctionIds() {
        return functionIds;
    }

    public void addFunction(EntityFunction entityFunction) {
        functions.add(entityFunction);
    }

    public void addTag(String tag) {
        tags.concat(tag);
    }

    public void addTag(String... moreTags) {
        for (String tag : moreTags) {
            tags.concat(tag);
        }
    }

    public boolean hasTag(String tag) {
        return tags.indexOf(tag) >= 0;
    }

    @Override
    public String toString() {
        return "Entity (" + type + ") " + getIid();
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public AtomMain getApp() {
        return app;
    }

    public void setApp(AtomMain app) {
        this.app = app;
    }

    public float getBoundingRadius() {
        return boundingRadius;
    }

    public void setBoundingRadius(float boundingRadius) {
        this.boundingRadius = boundingRadius;
    }
}
