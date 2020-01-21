package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.PersistentAvlTree;
import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;

import java.util.HashSet;
import java.util.UUID;

public class TransactionPersistentMap<K extends Comparable<K>, V> implements Transaction<PersistentMap<K, V>> {
    private PersistentMap<K, V> map = new PersistentMap<K, V>();

    public synchronized PersistentMap<K, V> startTransaction() {
        PersistentMap<K, V> newMap = new PersistentMap<K, V>();
        newMap.currentVersionNode = map.currentVersionNode;
        newMap.previousVersion = map.currentVersion;
        newMap.currentVersion = UUID.randomUUID();
        newMap.keysChange = new HashSet<>();
        newMap.oldMap = map.currentVersionNode.getValue();
        return newMap;
    }

    public synchronized PersistentMap<K, V> endTransaction(PersistentMap<K, V> transaction) {
        if (map.currentVersion == transaction.previousVersion) {
            map.currentVersion = transaction.currentVersion;
            Tree<UUID, PersistentAvlTree<K, V>> temp = new Tree<>(transaction.currentVersion, transaction.currentVersionNode.getValue());
            map.currentVersionNode.addChild(temp.getRoot());
            map.currentVersionNode = temp.getRoot();
            return map;
        }
        for (K key : transaction.keysChange) {
            if (transaction.oldMap.find(key) != map.currentVersionNode.getValue().find(key)) {
                return map;
            }
        }
        map.currentVersion = transaction.currentVersion;
        Tree<UUID, PersistentAvlTree<K, V>> temp = new Tree<>(transaction.currentVersion, transaction.currentVersionNode.getValue());
        map.currentVersionNode.addChild(temp.getRoot());
        map.currentVersionNode = temp.getRoot();
        return map;
    }
}
