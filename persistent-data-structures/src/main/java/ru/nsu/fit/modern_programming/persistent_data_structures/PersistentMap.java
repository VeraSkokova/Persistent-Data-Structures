package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.PersistentAvlTree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PersistentMap<K extends Comparable<K>, V> {
    private PersistentAvlTree<K, V> tree;

    public PersistentMap() {
    }

    public PersistentMap(PersistentAvlTree<K, V> tree) {
        this.tree = tree;
    }

    public int size() {
        return tree.itemsCount();
    }

    public boolean isEmpty() {
        return tree.itemsCount() == 0;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        return false;
    }

    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return tree.find((K) key);
    }

    public PersistentMap<K, V> put(K key, V value) {
        if (tree == null) {
            tree = new PersistentAvlTree<>(key, value);
            return this;
        }
        PersistentAvlTree<K, V> newTree = tree.insert(key, value);
        return new PersistentMap<>(newTree);
    }

    public PersistentMap<K, V> remove(Object key) {
        if (tree == null) {
            return null;
        }
        return new PersistentMap<>(tree.delete((K) key));
    }

    public void putAll(Map<? extends K, ? extends V> m) {

    }

    public void clear() {

    }

    public Set<K> keySet() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }
}
