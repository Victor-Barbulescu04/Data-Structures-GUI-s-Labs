/*
 * Course: CSC1120
 * Spring 2024
 * Lab 14 - HashTable
 * Name: Victor Barbulescu
 */
package barbulescuv.structures;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An arrayList of Entry tuples
 * @param <K>
 * @param <V>
 */
public class ListMap<K, V> implements Map<K, V> {

    private List<Entry<K, V>> entries;

    /**
     * ListMap Constructor
     */
    public ListMap(){
        entries = new ArrayList<>();
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        // Iterate through the entries and check if any entry has the specified key
        for (Entry<K, V> entry : entries) {
            if (Objects.equals(entry.getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        // Iterate through the entries and return the value if the key matches
        for (Entry<K, V> entry : entries) {
            if (Objects.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        // Iterate through the entries and update the value if the key matches
        for (Entry<K, V> entry : entries) {
            if (Objects.equals(entry.getKey(), key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        // If key doesn't exist, add a new entry
        entries.add(new AbstractMap.SimpleEntry<>(key, value));
        return null;
    }

    @Override
    public V remove(Object key) {
        // Iterate through the entries and remove the entry if the key matches
        Iterator<Entry<K, V>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (Objects.equals(entry.getKey(), key)) {
                V oldValue = entry.getValue();
                iterator.remove();
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        entries = new ArrayList<>();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>(entries);
    }
}
