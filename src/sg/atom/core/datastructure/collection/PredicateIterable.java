/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.datastructure.collection;

import com.google.common.base.Predicate;
import java.util.Iterator;

/**
 *
 * @author CuongNguyen
 */@Deprecated
public class PredicateIterable<T> implements Iterable<T> {
    public Iterable<T> iterable;
    public Predicate<T> predicate;
    public PredicateIterator<T> iterator = null;

    public PredicateIterable(Iterable<T> iterable, Predicate<T> predicate) {
        set(iterable, predicate);
    }

    public void set(Iterable<T> iterable, Predicate<T> predicate) {
        this.iterable = iterable;
        this.predicate = predicate;
    }

    /**
     * Returns an iterator. Note that the same iterator instance is returned
     * each time this method is called. Use the
     * {@link Predicate.PredicateIterator} constructor for nested or
     * multithreaded iteration.
     */
    @Override
    public Iterator<T> iterator() {
        if (iterator == null) {
            iterator = new PredicateIterator<T>(iterable.iterator(), predicate);
        } else {
            iterator.set(iterable.iterator(), predicate);
        }
        return iterator;
    }
    
}
