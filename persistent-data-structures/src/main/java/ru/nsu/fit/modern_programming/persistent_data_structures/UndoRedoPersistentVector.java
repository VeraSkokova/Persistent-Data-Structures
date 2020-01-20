package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;
import ru.nsu.fit.modern_programming.persistent_data_structures.trie.PersistentBitTrie;

import java.util.*;

public class UndoRedoPersistentVector<T> extends PersistentVector<T> implements UndoRedo {
    private Deque<Tree<UUID, PersistentBitTrie<T>>.Node> redoStack = new ArrayDeque<>();
    private List<UpdateListener> updateListeners = new ArrayList<>();

    private UpdateListener updateListener = persistentDataStructure -> {
        currentVersion = UUID.randomUUID();
        Tree<UUID, PersistentBitTrie<T>> temp = new Tree<>(currentVersion, currentVersionNode.getValue());
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
    public void registerUpdateListener(UpdateListener updateListener) {
        updateListeners.add(updateListener);
    }

    @Override
    public boolean add(T t) {
        if (t instanceof UndoRedo) {
            ((UndoRedo) t).registerUpdateListener(updateListener);
        }
        return super.add(t);
    }

    @Override
    public void add(int index, T element) {
        if (element instanceof UndoRedo) {
            ((UndoRedo) element).registerUpdateListener(updateListener);
        }
        super.add(index, element);
    }

    private void updateAllListeners() {
        for (UpdateListener listener : updateListeners) {
            listener.onUpdate(this);
        }
    }
}
