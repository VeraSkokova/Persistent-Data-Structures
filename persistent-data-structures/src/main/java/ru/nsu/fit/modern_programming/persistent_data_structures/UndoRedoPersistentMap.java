package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.PersistentAvlTree;
import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class UndoRedoPersistentMap<K extends Comparable<K>, V> extends PersistentMap<K, V> implements UndoRedo{
    private Deque<Tree<UUID, PersistentMap<K, V>>.Node> redoStack = new ArrayDeque<>();
    private Tree<UUID, PersistentMap<K, V>> history;
    Tree<UUID, PersistentMap<K, V>>.Node node;

    public UndoRedoPersistentMap() {
    }

    public UndoRedoPersistentMap(PersistentAvlTree<K, V> tree) {
        super(tree);
    }

    @Override
    public UndoRedo undo() {
        if (node.getParent() == null) {
            return this;
        }
        redoStack.push(node);
        node = node.getParent();

        return this;
    }

    @Override
    public UndoRedo redo() {
        if (redoStack.isEmpty()) {
            return this;
        }
        node = redoStack.pop();

        return this;
    }

    @Override
    public V get(Object key) {
        return node.getValue().get(key);
    }

    @Override
    public PersistentMap<K, V> put(K key, V value) {
        PersistentMap<K, V> put = super.put(key, value);
        return insert(put);
    }

    @Override
    public PersistentMap<K, V> remove(Object key) {
        PersistentMap<K, V> remove = super.remove(key);
        return insert(remove);
    }

    private PersistentMap<K, V> insert(PersistentMap<K, V> add) {
        if (history == null) {
            history = new Tree<>(UUID.randomUUID(), add);
            node = history.getRoot();
        } else {
            Tree<UUID, PersistentMap<K, V>>.Node child = new Tree<>(UUID.randomUUID(), add).getRoot();
            node.addChild(child);
            node = child;
        }
        return this;
    }
}
