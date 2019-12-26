package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.PersistentAvlTree;

import java.util.*;

public class PersistentMap<K extends Comparable<K>, V> implements Map<K, V> {
    private List<PersistentAvlTree<K, V>> versions = new ArrayList<>();
    private int currentVersion = 0;

    @Override
    public int size() {
        return versions.get(currentVersion).itemsCount();
    }

    @Override
    public boolean isEmpty() {
        return versions.get(currentVersion).itemsCount() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return versions.get(currentVersion).find((K) key);
    }

    @Override
    public V put(K key, V value) {
        V old = get(key);
        PersistentAvlTree<K, V> latestVersion = versions.get(currentVersion);
        PersistentAvlTree<K, V> newVersion = latestVersion.insert(key, value);
        versions.add(newVersion);
        currentVersion++;
        return old;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
