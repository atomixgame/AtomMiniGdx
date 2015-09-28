package sg.atom.corex.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import com.google.common.eventbus.EventBus;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.core.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;

/**
 * An simple EntityManager implementation which have basic Spatial - Entity
 * relationship management.
 *
 * <ul>
 *
 * <li>It has a Cache implementation of original entities beside of one in
 * AssetManager.</li>
 *
 * @author atomix
 */
@Deprecated
public class EntityManager extends AbstractManager {

    public static final Logger logger = LoggerFactory.getLogger(EntityManager.class.getName());
    protected EntityFactory entityFactory;
    // Entity management 
    protected ConcurrentMap<Long, ComposableEntity> entities = new MapMaker()
            .concurrencyLevel(10)
            .weakKeys()
            .weakValues().makeMap();
    protected HashMap<String, Node> nodes = new HashMap<String, Node>();
    private long totalEntityId = -1;
    public static long DEFAULT_NONE_ID = SpatialEntity.DEFAULT_NONE_ID;
    // Services
    private final EventBus eventBus;
    private final Stopwatch stopwatch;
    protected boolean collisionByBoundingVolume = true;
    protected boolean collisionByIntersect = true;

    public EntityManager(AtomMain app) {
        super(app);
        this.eventBus = new EventBus("EntityManager");
        this.stopwatch = Stopwatch.createStarted();
        this.customCycle = true;
    }

    public SpatialEntity checkCollision(Spatial spatial, Spatial... ignoredList) {
        List<SpatialEntity> allSpatialEntities = getAllSpatialEntities();

        for (SpatialEntity se : allSpatialEntities) {
            boolean ignored = ignoredList != null
                    && Arrays.asList(ignoredList).contains(se.getSpatial());

            if (se.getSpatial() != spatial && !ignored) {
                if (collisionByBoundingVolume) {
                    if (!collisionByIntersect) {
                        CollisionResults collisionResults = new CollisionResults();
                        se.getSpatial().getWorldBound().collideWith(spatial.getWorldBound(), collisionResults);
                        if (collisionResults.size() > 0) {
                            Geometry geo = collisionResults.getCollision(0).getGeometry();
                            SpatialEntity hittedSpatialEntity = getEntityFrom(geo);

                            return hittedSpatialEntity;
                        }
                    } else {
                        if (se.getSpatial().getWorldBound() != spatial.getWorldBound() && se.getSpatial().getWorldBound().intersects(spatial.getWorldBound())) {
//                            System.out.println("Collide with " + se.toString());
                            return se;
                        }
                    }
                } else {

                }
            }
        }
        return null;
    }

    public SpatialEntity checkCollision(SpatialEntity spatialEntity, Spatial... ignoredList) {
        return checkCollision(spatialEntity.getSpatial(), ignoredList);
    }

    public SpatialEntity checkCollision(Spatial spatial, Predicate<Spatial>... predicates) {
        List<SpatialEntity> allSpatialEntities = getAllSpatialEntities();

        for (SpatialEntity se : allSpatialEntities) {
            boolean ignored = false;

            for (Predicate<Spatial> pre : predicates) {
                if (pre.apply(se.getSpatial())) {
                    ignored = true;
                }
            }

            if (se.getSpatial() != spatial && !ignored) {
                if (collisionByBoundingVolume) {
                    if (!collisionByIntersect) {
                        CollisionResults collisionResults = new CollisionResults();
                        se.getSpatial().getWorldBound().collideWith(spatial.getWorldBound(), collisionResults);
                        if (collisionResults.size() > 0) {
                            Geometry geo = collisionResults.getCollision(0).getGeometry();
                            SpatialEntity hittedSpatialEntity = getEntityFrom(geo);

                            return hittedSpatialEntity;
                        }
                    } else {
                        if (se.getSpatial().getWorldBound() != spatial.getWorldBound() && se.getSpatial().getWorldBound().intersects(spatial.getWorldBound())) {
//                            System.out.println("Collide with " + se.toString());
                            return se;
                        }
                    }
                } else {

                }
            }
        }
        return null;
    }

    public SpatialEntity checkCollision(SpatialEntity spatialEntity, Predicate<SpatialEntity>... predicates) {
        List<SpatialEntity> allSpatialEntities = getAllSpatialEntities();

        for (SpatialEntity se : allSpatialEntities) {
            boolean ignored = false;

            for (Predicate<SpatialEntity> pre : predicates) {
                if (pre.apply(se)) {
                    ignored = true;
                }
            }

            if (se.getSpatial() != spatialEntity.getSpatial() && !ignored) {
                if (collisionByBoundingVolume) {
                    if (!collisionByIntersect) {
                        CollisionResults collisionResults = new CollisionResults();
                        se.getSpatial().getWorldBound().collideWith(spatialEntity.getSpatial().getWorldBound(), collisionResults);
                        if (collisionResults.size() > 0) {
                            Geometry geo = collisionResults.getCollision(0).getGeometry();
                            SpatialEntity hittedSpatialEntity = getEntityFrom(geo);

                            return hittedSpatialEntity;
                        }
                    } else {
                        if (se.getSpatial().getWorldBound() != spatialEntity.getSpatial().getWorldBound() && se.getSpatial().getWorldBound().intersects(spatialEntity.getSpatial().getWorldBound())) {
//                            System.out.println("Collide with " + se.toString());
                            return se;
                        }
                    }
                } else {

                }
            }
        }
        return null;
    }

    public static class EntityEvent {

        long timeStamp;
        ComposableEntity entity;
        long entityId;

        public EntityEvent(ComposableEntity e) {
            this.entity = e;
            this.entityId = e.getIid();
        }

        public EntityEvent(ComposableEntity e, long timeStamp) {
            this.timeStamp = timeStamp;
            this.entity = e;
            this.entityId = e.getIid();
        }

        public ComposableEntity getEntity() {
            return entity;
        }

        public long getEntityId() {
            return entityId;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }

    public static class EntityAddEvent extends EntityEvent {

        public EntityAddEvent(ComposableEntity e) {
            super(e);
        }

        public EntityAddEvent(ComposableEntity e, long timeStamp) {
            super(e, timeStamp);
        }
    }
//    public static class EntityPersistEvent{
//        
//    }

    public static class EntityRemovalEvent extends EntityEvent {

        public EntityRemovalEvent(ComposableEntity e, long timeStamp) {
            super(e, timeStamp);
        }

        public EntityRemovalEvent(ComposableEntity e) {
            super(e);
        }
    }
//    public static class EntityRemoteEvent {
//        
//    }
    /* Manage entities's type as primary lookup methods */

    public void registerEntityType() {
    }

    public void registerEntityTypes() {
    }

    public ArrayList<String> getEntityAssets() {
        return new ArrayList<String>();
    }

    public Long getNewEntityId() {
        totalEntityId++;
        return new Long(totalEntityId);
    }

    public void addEntity(SpatialEntity e) {
        if (e.iid == DEFAULT_NONE_ID) {
            Long newId = getNewEntityId();
            e.iid = newId;
        }
        entities.put(e.iid, e);
        eventBus.post(new EntityAddEvent(e, stopwatch.elapsed(TimeUnit.MICROSECONDS)));
    }

    public ComposableEntity removeEntity(Long id) {
        ComposableEntity e = entities.remove(id);
        eventBus.post(new EntityRemovalEvent(e, stopwatch.elapsed(TimeUnit.MICROSECONDS)));
        return e;
    }

    public ComposableEntity removeEntity(ComposableEntity e) {
        return removeEntity(e.getIid());
    }

    public boolean isEntitySpatial(Spatial selectableSpatial) {
        return true;
    }

    public boolean isHasNoId(SpatialEntity entity) {
        return entity.iid == SpatialEntity.DEFAULT_NONE_ID;
    }
    /* Search and filter over entities */

    public ArrayList<SpatialEntity> getAllSpatialEntities() {
        // do filter...
        ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();
        for (ComposableEntity entity : entities.values()) {
            if (entity instanceof SpatialEntity) {
                result.add((SpatialEntity) entity);
            }
        }
        //
        return result;
    }

    public ArrayList<SpatialEntity> getAllSpatialEntitiesByGroup(String groupName) {
        // do filter...
        ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();

        for (ComposableEntity entity : entities.values()) {
            //System.out.println(" ByGroup " + entity.id);
//            if (entity instanceof SpatialEntity) {
//                if (entity.getGroup().equals(groupName)) {
//                    result.add((SpatialEntity) entity);
//                }
//            }
        }
        //
        return result;
    }

    public <T extends ComposableEntity> ArrayList<T> getEntitiesByClass(Class<T> clazz) {
        // do filter...
        ArrayList<T> result = new ArrayList<T>();
        for (ComposableEntity entity : entities.values()) {
            if (clazz.isAssignableFrom(entity.getClass())) {
                result.add((T) entity);
            }
        }
        //
        return result;
    }
    //Cycle--------------------------------------------------------------------

    public void init() {
        this.entityFactory = new EntityFactory(app);
    }

    public void load() {
    }

    public void config(Configuration props) {
    }

    @Override
    public void update(float tpf) {
        if (actived) {
            if (!customCycle) {
                //For sometime we will require a consist view.
                Iterator<ComposableEntity> iterator = entities.values().iterator();
                while (iterator.hasNext()) {
                    ComposableEntity entity = iterator.next();
                    //FIXME: Only deal with spatial entity
                    if (entity instanceof SpatialEntity) {
                        ((SpatialEntity) entity).update(tpf);
                    }
                }
            }
        }
    }

    public void finish() {
    }
    //GETTER & SETTER
    // For Collections of Entities!-----------------------------------------

    public static List<ComposableEntity> getBy(List<ComposableEntity> characters, Predicate<ComposableEntity> predicate) {
        ArrayList<ComposableEntity> result = new ArrayList<ComposableEntity>();
        for (ComposableEntity gc : characters) {
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public static List<ComposableEntity> getBy(List<ComposableEntity> characters, Predicate<ComposableEntity>... filters) {
        ArrayList<ComposableEntity> result = new ArrayList<ComposableEntity>();
        for (ComposableEntity gc : characters) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public List<ComposableEntity> getBy(Predicate<ComposableEntity>... filters) {
        ArrayList<ComposableEntity> result = new ArrayList<ComposableEntity>();
        for (ComposableEntity entity : entities.values()) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(entity)) {
                result.add(entity);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public SpatialEntity getEntityFrom(Spatial sp) {
        SpatialEntity result = null;
        Iterator<ComposableEntity> iterator = entities.values().iterator();
        while (iterator.hasNext()) {
            ComposableEntity entity = iterator.next();
            if (entity instanceof SpatialEntity) {
                Spatial esp = ((SpatialEntity) entity).getSpatial();
                if (esp instanceof Node && sp.hasAncestor((Node) esp)) {
                    result = (SpatialEntity) entity;
                    break;
                } else if (sp == esp) {
                    result = (SpatialEntity) entity;
                    break;
                }
            }
        }
        return result;
    }

    public List<SpatialEntity> getAllEntitiesFrom(Node node) {
        final ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();

        return result;
    }

    public ComposableEntity getEntityById(long id) {
        return entities.get(id);
    }

    public void setEntityById(long id, ComposableEntity newEntity) {
        entities.put(id, newEntity);
    }

    public <T extends EntityFactory> T getEntityFactory(Class<T> clazz) {
        return (T) entityFactory;
    }

    public void setEntityFactory(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
