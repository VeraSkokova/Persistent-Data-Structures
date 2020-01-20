package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;
import ru.nsu.fit.modern_programming.persistent_data_structures.trie.PersistentBitTrie;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class UndoRedoPersistentVector<T> extends PersistentVector<T> implements UndoRedo {
    private Deque<Tree<UUID, PersistentBitTrie<T>>.Node> redoStack = new ArrayDeque<>();

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
