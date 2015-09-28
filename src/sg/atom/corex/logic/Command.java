package sg.atom.corex.logic;

/**
 *
 * @author cuong.nguyen
 */
public interface Command<T> {
    void execute(T object);
}
