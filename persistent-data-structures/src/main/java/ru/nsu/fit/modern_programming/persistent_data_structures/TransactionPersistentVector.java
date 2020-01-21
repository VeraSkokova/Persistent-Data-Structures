package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;
import ru.nsu.fit.modern_programming.persistent_data_structures.trie.PersistentBitTrie;

import java.util.HashSet;
import java.util.UUID;

public class TransactionPersistentVector<V> implements Transaction<PersistentVector<V>> {
    private PersistentVector<V> vector = new PersistentVector<>();

    public synchronized PersistentVector<V> startTransaction() {
        PersistentVector<V> newVector = new PersistentVector<>();
        newVector.currentVersionNode = vector.currentVersionNode;
        newVector.previousVersion = vector.currentVersion;
        newVector.currentVersion = UUID.randomUUID();
        newVector.indexesInsertion = new HashSet<>();
        newVector.indexDeletion = -1;
        newVector.oldVector = vector.currentVersionNode.getValue();
        return newVector;
    }

    public synchronized PersistentVector<V> endTransaction(PersistentVector<V> transaction) {
        if (vector.currentVersion == transaction.previousVersion) {
            vector.currentVersion = transaction.currentVersion;
            Tree<UUID, PersistentBitTrie<V>> temp = new Tree<>(transaction.currentVersion, transaction.currentVersionNode.getValue());
            vector.currentVersionNode.addChild(temp.getRoot());
            vector.currentVersionNode = temp.getRoot();
            return vector;
        }
        if (transaction.indexDeletion >= 0) {
            if (transaction.oldVector.getSize() != vector.currentVersionNode.getValue().getSize()) {
                return vector;
            }
            for (int i = transaction.indexDeletion; i < transaction.oldVector.getSize(); i++) {
                if (transaction.oldVector.lookup(i) != vector.currentVersionNode.getValue().lookup(i)) {
                    return vector;
                }
            }
        }
        for (int index : transaction.indexesInsertion) {
            if (index >= transaction.indexDeletion) continue;
            if (transaction.oldVector.lookup(index) != vector.currentVersionNode.getValue().lookup(index)) {
                return vector;
            }
        }
        vector.currentVersion = transaction.currentVersion;
        Tree<UUID, PersistentBitTrie<V>> temp = new Tree<>(transaction.currentVersion, transaction.currentVersionNode.getValue());
        vector.currentVersionNode.addChild(temp.getRoot());
        vector.currentVersionNode = temp.getRoot();
        return vector;
    }
}