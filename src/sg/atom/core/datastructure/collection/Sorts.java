/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.datastructure.collection;

import java.util.Comparator;

/**
 * Provides methods to sort arrays of objects. Sorting requires working memory
 * and this class allows that memory to be reused to avoid allocation. The
 * sorting is otherwise identical to the Arrays.sort methods (uses timsort).
 *
 * <p> Note that sorting primitive arrays with the Arrays.sort methods does not
 * allocate memory (unless sorting large arrays of char, short, or byte).
 *
 * FIXME: Replace usages with other Sorts.
 * 
 * @author Nathan Sweet,
 */
@Deprecated
public class Sorts {

    static private Sorts instance;
    private TimSort timSort;
    private ComparableTimSort comparableTimSort;

    public <T> void sort(Array<T> a) {
        if (comparableTimSort == null) {
            comparableTimSort = new ComparableTimSort();
        }
        comparableTimSort.doSort((Object[]) a.items, 0, a.size);
    }

    public <T> void sort(T[] a) {
        if (comparableTimSort == null) {
            comparableTimSort = new ComparableTimSort();
        }
        comparableTimSort.doSort(a, 0, a.length);
    }

    public <T> void sort(T[] a, int fromIndex, int toIndex) {
        if (comparableTimSort == null) {
            comparableTimSort = new ComparableTimSort();
        }
        comparableTimSort.doSort(a, fromIndex, toIndex);
    }

    public <T> void sort(Array<T> a, Comparator<T> c) {
        if (timSort == null) {
            timSort = new TimSort();
        }
        timSort.doSort((Object[]) a.items, (Comparator) c, 0, a.size);
    }

    public <T> void sort(T[] a, Comparator<T> c) {
        if (timSort == null) {
            timSort = new TimSort();
        }
        timSort.doSort(a, c, 0, a.length);
    }

    public <T> void sort(T[] a, Comparator<T> c, int fromIndex, int toIndex) {
        if (timSort == null) {
            timSort = new TimSort();
        }
        timSort.doSort(a, c, fromIndex, toIndex);
    }

    /**
     * Returns a Sort instance for convenience. Multiple threads must not use
     * this instance at the same time.
     */
    static public Sorts instance() {
        if (instance == null) {
            instance = new Sorts();
        }
        return instance;
    }
}
