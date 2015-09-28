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
public class PredicateIterator<T> implements Iterator<T> {
    public Iterator<T> iterator;
    public Predicate<T> predicate;
    public boolean end = false;
    public boolean peeked = false;
    public T next = null;

    public PredicateIterator(final Iterable<T> iterable, final Predicate<T> predicate) {
        this(iterable.iterator(), predicate);
    }

    public PredicateIterator(final Iterator<T> iterator, final Predicate<T> predicate) {
        set(iterator, predicate);
    }

    public void set(final Iterable<T> iterable, final Predicate<T> predicate) {
        set(iterable.iterator(), predicate);
    }

    public void set(final Iterator<T> iterator, final Predicate<T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
        end = peeked = false;
        next = null;
    }

    @Override
    public boolean hasNext() {
        if (end) {
            return false;
        }
        if (next != null) {
            return true;
        }
        peeked = true;
        while (iterator.hasNext()) {
            final T n = iterator.next();
            if (predicate.apply(n)) {
                next = n;
                return true;
            }
        }
        end = true;
        return false;
    }

    @Override
    public T next() {
        if (next == null && !hasNext()) {
            return null;
        }
        final T result = next;
        next = null;
        peeked = false;
        return result;
    }

    @Override
    public void remove() {
        if (peeked) {
            throw new RuntimeException("Cannot remove between a call to hasNext() and next().");
        }
        iterator.remove();
    }
    
}
