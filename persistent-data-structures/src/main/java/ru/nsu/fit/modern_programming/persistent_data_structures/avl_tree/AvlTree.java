package ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree;

public class AvlTree<K extends Comparable<K>, V> {
    protected K key;
    protected V value;
    protected int height;
    protected AvlTree<K, V> left;
    protected AvlTree<K, V> right;

    protected int nodesCount;

    public AvlTree(K key, V value) {
        this.key = key;
        this.value = value;
        this.height = 1;
        this.nodesCount = 1;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public K getKey() {
        return key;
    }

    public AvlTree<K, V> getLeft() {
        return left;
    }

    public AvlTree<K, V> getRight() {
        return right;
    }

    public AvlTree<K, V> withLeft(AvlTree<K, V> left) {
        this.left = left;
        return this;
    }

    public AvlTree<K, V> withRight(AvlTree<K, V> right) {
        this.right = right;
        return this;
    }

    protected int getBalanceFactor() {
        if (right == null && left == null) {
            return 0;
        }
        if (right == null) {
            return -left.getHeight();
        }
        if (left == null) {
            return right.getHeight();
        }
        return right.getHeight() - left.getHeight();
    }

    protected void recountHeight() {
        int heightLeft = left != null ? left.getHeight() : 0;
        int heightRight = right != null ? right.getHeight() : 0;
        this.height = (heightLeft > heightRight ? heightLeft : heightRight) + 1;
    }

    private AvlTree<K, V> rotateRight() {
        AvlTree<K, V> newRoot = left;
        this.left = newRoot.right;
        newRoot.right = this;
        this.recountHeight();
        newRoot.recountHeight();
        return newRoot;
    }

    private AvlTree<K, V> rotateLeft() {
        AvlTree<K, V> newRoot = right;
        this.right = newRoot.left;
        newRoot.left = this;
        this.recountHeight();
        newRoot.recountHeight();
        return newRoot;
    }

    private AvlTree<K, V> balance() {
        recountHeight();
        if (getBalanceFactor() == 2) {
            if (right.getBalanceFactor() < 0) {
                right = right.rotateRight();
            }
            return rotateLeft();
        }
        if (getBalanceFactor() == -2) {
            if (left.getBalanceFactor() > 0) {
                left = left.rotateLeft();
            }
            return rotateRight();
        }
        return this;
    }

    public AvlTree<K, V> insert(K anotherKey, V value) {
        if (key.compareTo(anotherKey) < 0) {
            if (this.left == null) {
                this.left = new AvlTree<>(anotherKey, value);
            } else {
                this.left = this.left.insert(anotherKey, value);
            }
        } else if (key.compareTo(anotherKey) > 0) {
            if (this.right == null) {
                this.right = new AvlTree<>(anotherKey, value);
            } else {
                this.right = this.right.insert(anotherKey, value);
            }
        } else {
            this.value = value;
            nodesCount--;
        }
        nodesCount++;
        return this.balance();
    }

    public V find(K key) {
        AvlTree<K, V> x = this;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x.value;
            }
            if (cmp < 0) {
                x = x.right;
            } else {
                x = x.left;
            }
        }
        return null;
    }

    public AvlTree<K, V> delete(K key) {
        if (this.find(key) == null) {
            return this;
        }
        return this.delete(key, this);
    }

    protected AvlTree<K, V> minValueNode(AvlTree<K, V> node) {
        AvlTree<K, V> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private AvlTree<K, V> delete(K key, AvlTree<K, V> root) {
        if (root == null) {
            return root;
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = this.delete(key, root.left);
        } else if (cmp > 0) {
            root.right = this.delete(key, root.right);
        } else {
            if ((root.left == null) || (root.right == null)) {
                AvlTree<K, V> temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
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
                root.right = this.delete(temp.key, root.right);
            }
        }
        if (root == null) {
            return root;
        }
        root.nodesCount--;
        root.recountHeight();
        int balance = root.getBalanceFactor();
        if (balance > 1 && root.left.getBalanceFactor() >= 0) {
            return root.rotateRight();
        }
        if (balance > 1 && root.left.getBalanceFactor() < 0) {
            root.left = root.left.rotateLeft();
            return root.rotateRight();
        }
        if (balance < -1 && root.right.getBalanceFactor() <= 0) {
            return root.rotateLeft();
        }
        if (balance < -1 && root.right.getBalanceFactor() > 0) {
            root.right = root.right.rotateRight();
            return root.rotateLeft();
        }
        return root;
    }

    public int itemsCount() {
        return nodesCount;
    }
}
