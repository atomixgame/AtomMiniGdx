/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.datastructure.collection;

import gnu.trove.map.hash.THashMap;

import java.util.*;

/**
 *
 * @author CuongNguyen
 */
public class TBidiMap<K, V> implements Map<K, V> {

    private final Map<K, V> myKeyToValueMap = new THashMap<K, V>();
    private final Map<V, List<K>> myValueToKeysMap = new THashMap<V, List<K>>();

    @Override
    public V put(K key, V value) {
        V oldValue = myKeyToValueMap.put(key, value);
        if (oldValue != null) {
            if (oldValue.equals(value)) {
                return oldValue;
            }
            List<K> array = myValueToKeysMap.get(oldValue);
            array.remove(key);
        }

        List<K> array = myValueToKeysMap.get(value);
        if (array == null) {
            array = new ArrayList<K>();
            myValueToKeysMap.put(value, array);
        }
        array.add(key);
        return oldValue;
    }

    @Override
    public void clear() {
        myKeyToValueMap.clear();
        myValueToKeysMap.clear();
    }

    public List<K> getKeysByValue(V value) {
        return myValueToKeysMap.get(value);
    }

    @Override
    public Set<K> keySet() {
        return myKeyToValueMap.keySet();
    }

    @Override
    public int size() {
        return myKeyToValueMap.size();
    }

    @Override
    public boolean isEmpty() {
        return myKeyToValueMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return myKeyToValueMap.containsKey(key);
    }

    @Override
    @SuppressWarnings({"SuspiciousMethodCalls"})
    public boolean containsValue(Object value) {
        return myValueToKeysMap.containsKey(value);
    }

    @Override
    public V get(Object key) {
        return myKeyToValueMap.get(key);
    }

    public void removeValue(V v) {
        List<K> ks = myValueToKeysMap.remove(v);
        if (ks != null) {
            for (K k : ks) {
                myKeyToValueMap.remove(k);
            }
        }
    }

    @Override
    @SuppressWarnings({"SuspiciousMethodCalls"})
    public V remove(Object key) {
        final V value = myKeyToValueMap.remove(key);
        final List<K> ks = myValueToKeysMap.get(value);
        if (ks != null) {
            if (ks.size() > 1) {
                ks.remove(key);
            } else {
                myValueToKeysMap.remove(value);
            }
        }
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> t) {
        for (final K k1 : t.keySet()) {
            put(k1, t.get(k1));
        }
    }

    @Override
    public Collection<V> values() {
        return myValueToKeysMap.keySet();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return myKeyToValueMap.entrySet();
    }

    @Override
    public String toString() {
        return new HashMap<K, V>(myKeyToValueMap).toString();
    }
}
