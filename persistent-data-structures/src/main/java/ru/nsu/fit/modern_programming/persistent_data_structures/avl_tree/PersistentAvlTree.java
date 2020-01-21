package ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree;

public class PersistentAvlTree<K extends Comparable<K>, V> extends AvlTree<K, V> {
    public PersistentAvlTree(K key, V value) {
        super(key, value);
    }

    private PersistentAvlTree<K, V> rotateRight() {
        PersistentAvlTree<K, V> newRoot = new PersistentAvlTree<K, V>(left.key, left.value);
        newRoot.nodesCount = left.nodesCount;
        newRoot.left = left.left;
        left = left.right;
        newRoot.right = this;
        this.recountHeight();
        newRoot.recountHeight();
        return newRoot;
    }

    private PersistentAvlTree<K, V> rotateLeft() {
        PersistentAvlTree<K, V> newRoot = new PersistentAvlTree<K, V>(right.key, right.value);
        newRoot.nodesCount = right.nodesCount;
        newRoot.right = right.right;
        right = right.left;
        newRoot.left = this;
        this.recountHeight();
        newRoot.recountHeight();
        return newRoot;
    }

    private PersistentAvlTree<K, V> balance() {
        recountHeight();
        if (getBalanceFactor() == 2) {
            if (right.getBalanceFactor() < 0) {
                PersistentAvlTree<K, V> newRight = new PersistentAvlTree<K, V>(right.key, right.value);
                newRight.nodesCount = right.nodesCount;
                newRight.left = right.left;
                newRight.right = right.right;
                right = newRight.rotateRight();
            }
            return rotateLeft();
        }
        if (getBalanceFactor() == -2) {
            if (left.getBalanceFactor() > 0) {
                PersistentAvlTree<K, V> newLeft = new PersistentAvlTree<K, V>(left.key, left.value);
                newLeft.nodesCount = left.nodesCount;
                newLeft.left = left.left;
                newLeft.right = left.right;
                left = newLeft.rotateLeft();
            }
            return rotateRight();
        }
        return this;
    }

    private PersistentAvlTree<K, V> insert(K anotherKey, V anotherValue, AvlTree<K, V> root) {
        PersistentAvlTree<K, V> result = new PersistentAvlTree<>(root.key, root.value);
        result.nodesCount = root.nodesCount;
        if (root.key.compareTo(anotherKey) < 0) {
            result.withRight(root.right);
            if (root.left == null) {
                result.withLeft(new PersistentAvlTree<>(anotherKey, anotherValue));
            } else {
                result.withLeft(this.insert(anotherKey, anotherValue, root.left));
            }
        } else if (root.key.compareTo(anotherKey) > 0) {
            result.withLeft(root.left);
            if (root.right == null) {
                result.withRight(new PersistentAvlTree<>(anotherKey, anotherValue));
            } else {
                result.withRight(this.insert(anotherKey, anotherValue, root.right));
            }
        } else {
            result.value = anotherValue;
            result.nodesCount--;
        }
        result.nodesCount++;
        return result.balance();
    }

    @Override
    public PersistentAvlTree<K, V> insert(K anotherKey, V anotherValue) {
        return this.insert(anotherKey, anotherValue, this);
    }

    @Override
    public PersistentAvlTree<K, V> delete(K key) {
        if (this.find(key) == null) {
            return this;
        }
        PersistentAvlTree<K, V> result = new PersistentAvlTree<>(key, value);
        result.nodesCount = nodesCount;
        result.left = left;
        result.right = right;
        return this.delete(key, result);
    }

    private PersistentAvlTree<K, V> delete(K key, PersistentAvlTree<K, V> root) {
        if (root == null) {
            return root;
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            PersistentAvlTree<K, V> newLeft = new PersistentAvlTree<K, V>(root.left.key, root.left.value);
            newLeft.nodesCount = root.left.nodesCount;
            newLeft.left = root.left.left;
            newLeft.right = root.left.right;
            root.left = this.delete(key, newLeft);
        } else if (cmp > 0) {
            PersistentAvlTree<K, V> newRight = new PersistentAvlTree<K, V>(root.right.key, root.right.value);
            newRight.nodesCount = root.right.nodesCount;
            newRight.left = root.right.left;
            newRight.right = root.right.right;
            root.right = this.delete(key, newRight);
        } else {
            if ((root.left == null) || (root.right == null)) {
                PersistentAvlTree<K, V> temp = null;
                if (temp == root.left) {
                    PersistentAvlTree<K, V> newRight = new PersistentAvlTree<K, V>(root.right.key, root.right.value);
                    newRight.nodesCount = root.right.nodesCount;
                    newRight.left = root.right.left;
                    newRight.right = root.right.right;
                    temp = newRight;
                } else {
                    PersistentAvlTree<K, V> newLeft = new PersistentAvlTree<K, V>(root.left.key, root.left.value);
                    newLeft.nodesCount = root.left.nodesCount;
                    newLeft.left = root.left.left;
                    newLeft.right = root.left.right;
                    temp = newLeft;
                }
                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                AvlTree<K, V> temp = minValueNode(root.right);
                root.key = temp.key;
                PersistentAvlTree<K, V> newRight = new PersistentAvlTree<K, V>(root.right.key, root.right.value);
                newRight.nodesCount = root.right.nodesCount;
                newRight.left = root.right.left;
                newRight.right = root.right.right;
                root.right = this.delete(temp.key, newRight);
            }
        }
        if (root == null) {
            return root;
        }
        root.recountHeight();
        int balance = root.getBalanceFactor();
        if (balance > 1 && root.left.getBalanceFactor() >= 0) {
            return root.rotateRight();
        }
        if (balance > 1 && root.left.getBalanceFactor() < 0) {
            PersistentAvlTree<K, V> newLeft = new PersistentAvlTree<K, V>(root.left.key, root.left.value);
            newLeft.nodesCount = root.left.nodesCount;
            newLeft.left = root.left.left;
            newLeft.right = root.left.right;
            root.left = newLeft.rotateLeft();
            return root.rotateRight();
        }
        if (balance < -1 && root.right.getBalanceFactor() <= 0) {
            return root.rotateLeft();
        }
        if (balance < -1 && root.right.getBalanceFactor() > 0) {
            PersistentAvlTree<K, V> newRight = new PersistentAvlTree<K, V>(root.right.key, root.right.value);
            newRight.nodesCount = root.right.nodesCount;
            newRight.left = root.right.left;
            newRight.right = root.right.right;
            root.right = newRight.rotateRight();
            return root.rotateLeft();
        }
        return root;
    }
}
