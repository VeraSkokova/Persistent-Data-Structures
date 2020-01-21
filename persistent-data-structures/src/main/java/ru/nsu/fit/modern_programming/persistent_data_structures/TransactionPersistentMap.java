package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;

import java.util.HashSet;
import java.util.UUID;

public class TransactionPersistentMap<K extends Comparable<K>, V> implements Transaction<UndoRedoPersistentMap<K, V>> {
    private UndoRedoPersistentMap<K, V> map = new UndoRedoPersistentMap<>();

    public synchronized UndoRedoPersistentMap<K, V> startTransaction() {
        UndoRedoPersistentMap<K, V> newMap = new UndoRedoPersistentMap<>();
        newMap.node = map.node;
        newMap.previousVersion = map.currentVersion;
        newMap.currentVersion = UUID.randomUUID();
        newMap.keysChange = new HashSet<>();
        newMap.oldMap = map.node.getValue();
        return newMap;
    }

    public synchronized UndoRedoPersistentMap<K, V> endTransaction(UndoRedoPersistentMap<K, V> transaction) {
        if (map.currentVersion == transaction.previousVersion) {
            map.currentVersion = transaction.currentVersion;
            Tree<UUID, PersistentMap<K, V>> temp = new Tree<>(transaction.currentVersion, transaction.node.getValue());
            map.node.addChild(temp.getRoot());
            map.node = temp.getRoot();
            return map;
        }
        for (K key : transaction.keysChange) {
            if (transaction.oldMap.get(key) != map.node.getValue().get(key)) {
                return map;
            }
        }
        map.currentVersion = transaction.currentVersion;
        Tree<UUID, PersistentMap<K, V>> temp = new Tree<>(transaction.currentVersion, transaction.node.getValue());
        map.node.addChild(temp.getRoot());
        map.node = temp.getRoot();
        return map;
    }
}
