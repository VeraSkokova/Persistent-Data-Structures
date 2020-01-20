package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.PersistentAvlTree;
import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;

import java.util.*;

public class UndoRedoPersistentMap<K extends Comparable<K>, V> extends PersistentMap<K, V> implements UndoRedo {
    private Deque<Tree<UUID, PersistentAvlTree<K, V>>.Node> redoStack = new ArrayDeque<>();
    private List<UpdateListener> updateListeners = new ArrayList<>();

    private UpdateListener updateListener = persistentDataStructure -> {
        currentVersion = UUID.randomUUID();
        Tree<UUID, PersistentAvlTree<K, V>> temp = new Tree<>(currentVersion, currentVersionNode.getValue());
        currentVersionNode.addChild(temp.getRoot());
        currentVersionNode = temp.getRoot();
    };

    @Override
    public UndoRedo undo() {
        if (currentVersionNode.getParent() == null) {
            return this;
        }
        redoStack.push(currentVersionNode);
        currentVersionNode = currentVersionNode.getParent();
        currentVersion = currentVersionNode.getKey();

        updateAllListeners();
        return this;
    }

    @Override
    public UndoRedo redo() {
        if (redoStack.isEmpty()) {
            return this;
        }
        currentVersionNode = redoStack.pop();
        currentVersion = currentVersionNode.getKey();

        updateAllListeners();
        return this;
    }

    @Override
    public V put(K key, V value) {
        if (value instanceof UndoRedo) {
            ((UndoRedo) value).registerUpdateListener(updateListener);
        }
        return super.put(key, value);
    }

    @Override
    public void registerUpdateListener(UpdateListener updateListener) {
        updateListeners.add(updateListener);
    }

    private void updateAllListeners() {
        for (UpdateListener listener : updateListeners) {
            listener.onUpdate(this);
        }
    }
}
