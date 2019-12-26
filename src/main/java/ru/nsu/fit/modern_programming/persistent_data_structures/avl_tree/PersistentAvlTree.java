package ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree;

public class PersistentAvlTree<K extends Comparable<K>, V> extends AvlTree<K, V> {
    public PersistentAvlTree(K key, V value) {
        super(key, value);
    }

    @Override
    public PersistentAvlTree<K, V> insert(K anotherKey, V anotherValue) {
        PersistentAvlTree<K, V> result = new PersistentAvlTree<>(key, value);
        result.nodesCount = nodesCount;
        if (key.compareTo(anotherKey) < 0) {
            result.withRight(right);
            if (left == null) {
                result.withLeft(new PersistentAvlTree<>(anotherKey, anotherValue));
            } else {
                result.withLeft(left.insert(anotherKey, anotherValue));
            }
        } else {
            result.withLeft(left);
            if (right == null) {
                result.withRight(new PersistentAvlTree<>(anotherKey, anotherValue));
            } else {
                result.withRight(right.insert(anotherKey, anotherValue));
            }
        }
        result.nodesCount++;
        return result;
    }
}
