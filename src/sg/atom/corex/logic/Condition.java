package sg.atom.corex.logic;

import com.google.common.base.Predicate;
import java.util.ArrayList;

/**
 * Condition is primitive logic brick of a Game framework. It's simply a warper
 * of Boolean, and boolean check. It has a definition, description and id which
 * means it belong to a context, has clean semantic and such.
 *
 * <p> Use Predicate instead! Moved to Predicate!
 *
 * @author atomix
 */
public abstract class Condition<T> implements Predicate<T> {

    protected boolean _status = false;

    public String getDefinition() {
        return null;
    }

    public long getId() {
        return 0;
    }

    public String getDescription() {
        return null;
    }
}
