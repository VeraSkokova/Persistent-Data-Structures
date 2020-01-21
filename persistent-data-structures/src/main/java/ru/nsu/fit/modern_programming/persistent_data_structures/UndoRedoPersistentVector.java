package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;
import ru.nsu.fit.modern_programming.persistent_data_structures.trie.PersistentBitTrie;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.UUID;

public class UndoRedoPersistentVector<T> extends PersistentVector<T> implements UndoRedo {
    public HashSet<Integer> indexesInsertion;
    public int indexDeletion;
    public PersistentVector<T> oldVector;
    UUID currentVersion;
    UUID previousVersion;
    private Deque<Tree<UUID, PersistentVector<T>>.Node> redoStack = new ArrayDeque<>();
    private Tree<UUID, PersistentVector<T>> history;
    Tree<UUID, PersistentVector<T>>.Node node;

    public UndoRedoPersistentVector() {
    }

    public UndoRedoPersistentVector(PersistentBitTrie<T> persistentBitTrie) {
        super(persistentBitTrie);
    }

    @Override
    public UndoRedo undo() {
        if (node.getParent() == null) {
            return this;
        }
        redoStack.push(node);
        node = node.getParent();
        currentVersion = node.getKey();

        return this;
    }

    @Override
    public UndoRedo redo() {
        if (redoStack.isEmpty()) {
            return this;
        }
        node = redoStack.pop();
        currentVersion = node.getKey();

        return this;
    }

    @Override
    public PersistentVector<T> add(T t) {
        PersistentVector<T> add = super.add(t);
        return insert(add);
    }

    @Override
    public PersistentVector<T> add(int index, T element) {
        PersistentVector<T> add = super.add(index, element);
        return insert(add);
    }

    private PersistentVector<T> insert(PersistentVector<T> add) {
        if (history == null) {
            history = new Tree<>(UUID.randomUUID(), add);
            node = history.getRoot();
        } else {
            Tree<UUID, PersistentVector<T>>.Node child = new Tree<>(UUID.randomUUID(), add).getRoot();
            node.addChild(child);
            node = child;
        }
        return this;
    }

    @Override
    public UndoRedoPersistentVector<T> remove(int index) {
        PersistentVector<T> remove = super.remove(index);
        insert(remove);
        return this;
    }

    @Override
    public T get(int index) {
        return node.getValue().get(index);
    }
}
