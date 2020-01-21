package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;

import java.util.HashSet;
import java.util.UUID;

public class TransactionPersistentVector<V> implements Transaction<UndoRedoPersistentVector<V>> {
    private UndoRedoPersistentVector<V> vector = new UndoRedoPersistentVector<>();

    public synchronized UndoRedoPersistentVector<V> startTransaction() {
        UndoRedoPersistentVector<V> newVector = new UndoRedoPersistentVector<>();
        newVector.node = vector.node;
        newVector.previousVersion = vector.currentVersion;
        newVector.currentVersion = UUID.randomUUID();
        newVector.indexesInsertion = new HashSet<>();
        newVector.indexDeletion = -1;
        newVector.oldVector = vector.node.getValue();
        return newVector;
    }

    public synchronized UndoRedoPersistentVector<V> endTransaction(UndoRedoPersistentVector<V> transaction) {
        if (vector.currentVersion == transaction.previousVersion) {
            vector.currentVersion = transaction.currentVersion;
            Tree<UUID, PersistentVector<V>> temp = new Tree<>(transaction.currentVersion, transaction.node.getValue());
            vector.node.addChild(temp.getRoot());
            vector.node = temp.getRoot();
            return vector;
        }
        if (transaction.indexDeletion >= 0) {
            if (transaction.oldVector.size() != vector.node.getValue().size()) {
                return vector;
            }
            for (int i = transaction.indexDeletion; i < transaction.oldVector.size(); i++) {
                if (transaction.oldVector.get(i) != vector.node.getValue().get(i)) {
                    return vector;
                }
            }
        }
        for (int index : transaction.indexesInsertion) {
            if (index >= transaction.indexDeletion) continue;
            if (transaction.oldVector.get(index) != vector.node.getValue().get(index)) {
                return vector;
            }
        }
        vector.currentVersion = transaction.currentVersion;
        Tree<UUID, PersistentVector<V>> temp = new Tree<>(transaction.currentVersion, transaction.node.getValue());
        vector.node.addChild(temp.getRoot());
        vector.node = temp.getRoot();
        return vector;
    }
}