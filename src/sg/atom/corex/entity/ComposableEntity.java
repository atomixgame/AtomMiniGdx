package sg.atom.corex.entity;

import com.simsilica.es.EntityId;

/**
 * Simple Entity interface
 *
 * @author cuong.nguyen
 */
public interface ComposableEntity {

    EntityId getId();

    long getIid();

    void compose(Object... components);

    void persist();
}
