package ru.nsu.fit.modern_programming.persistent_data_structures;

import java.util.*;

public class Tree<T> {
    private T value;
    private List<Tree<T>> children;

    public Tree(T value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public static <T> Tree<T> of(T value) {
        return new Tree<>(value);
    }

    public Tree<T> addChild(T value) {
        Tree<T> newChild = new Tree<>(value);
        children.add(newChild);
        return newChild;
    }

    public Optional<T> search(T value) {
        Queue<Tree<T>> queue = new ArrayDeque<>();
        queue.add(this);
        while(!queue.isEmpty()) {
            Tree<T> currentNode = queue.remove();
            if (currentNode.getValue().equals(value)) {
                return Optional.of(currentNode.getValue());
            } else {
                queue.addAll(currentNode.getChildren());
            }
        }
        return Optional.empty();
    }

    private List<Tree<T>> getChildren() {
        return children;
    }

    private T getValue() {
        return value;
    }
}
