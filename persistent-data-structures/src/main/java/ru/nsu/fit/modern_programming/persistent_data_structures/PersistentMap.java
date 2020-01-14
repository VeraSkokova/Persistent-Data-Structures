package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.PersistentAvlTree;
import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;

import java.util.*;

public class PersistentMap<K extends Comparable<K>, V> implements Map<K, V>, UndoRedo {
    private Tree<UUID, PersistentAvlTree<K, V>> versions;
    private Tree<UUID, PersistentAvlTree<K, V>>.Node currentVersionNode;
    private UUID currentVersion;

    private Deque<Tree<UUID, PersistentAvlTree<K, V>>.Node> redoStack = new ArrayDeque<>();

    @Override
    public int size() {
        return currentVersionNode.getValue().itemsCount();
    }

    @Override
    public boolean isEmpty() {
        return currentVersionNode.getValue().itemsCount() == 0;
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
        if (key == null) {
            throw new NullPointerException();
        }
        return currentVersionNode.getValue().find((K) key);
    }

    @Override
    public V put(K key, V value) {
        V old = get(key);
        PersistentAvlTree<K, V> latestVersion = currentVersionNode != null ? currentVersionNode.getValue() : null;
        if (latestVersion != null) {
            PersistentAvlTree<K, V> newVersion = latestVersion.insert(key, value);
            currentVersion = UUID.randomUUID();
            Tree<UUID, PersistentAvlTree<K, V>> temp = new Tree<>(currentVersion, newVersion);
            currentVersionNode.addChild(temp.getRoot());
            currentVersionNode = temp.getRoot();
        } else {
            latestVersion = new PersistentAvlTree<>(key, value);
            currentVersion = UUID.randomUUID();
            versions = new Tree<>(currentVersion, latestVersion);
            currentVersionNode = versions.getRoot();
        }
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

    @Override
    public UndoRedo undo() {
        if (currentVersionNode.getParent() == null) {
            return this;
        }
        redoStack.push(currentVersionNode);
        currentVersionNode = currentVersionNode.getParent();
        currentVersion = currentVersionNode.getKey();
        return this;
    }

    @Override
    public UndoRedo redo() {
        if (redoStack.isEmpty()) {
            return this;
        }
        currentVersionNode = redoStack.pop();
        currentVersion = currentVersionNode.getKey();
        return this;
    }
}
