package ru.nsu.fit.modern_programming.persistent_data_structures.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Tree<K, V> {
    private Node root;

    public Tree(K key, V value) {
        root = new Node();
        root.key = key;
        root.value = value;
        root.children = new ArrayList<>();
    }

    public Node getRoot() {
        return root;
    }

    public class Node {
        private K key;
        private V value;
        private Node parent;
        private List<Node> children;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Node getParent() {
            return parent;
        }

        public void addChild(Node child) {
            children.add(child);
            child.parent = this;
        }
    }

    public V find(K key) {
        Deque<Node> stack = new ArrayDeque<>();
        Node current = root;
        stack.push(root);

        while(!stack.isEmpty()) {
            current = stack.pop();
            if (key.equals(current.key)) {
                return current.value;
            }

            if (current.children != null) {
                for (Node node : current.children) {
                    stack.push(node);
                }
            }
        }

        return null;
    }


}
