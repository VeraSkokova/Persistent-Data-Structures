package ru.nsu.fit.modern_programming.persistent_data_structures.linked_list;

import ru.nsu.fit.modern_programming.persistent_data_structures.PersistentCollection;
import ru.nsu.fit.modern_programming.persistent_data_structures.UndoRedo;
import ru.nsu.fit.modern_programming.persistent_data_structures.VersionNode;

import java.util.Iterator;
import java.util.Stack;
import java.util.UUID;

public class PersistentLinkedList<E> implements PersistentList<E>, UndoRedo {

    private VersionNode<PersistentLinkedList<E>> currentVersion;

    private Stack<VersionNode<PersistentLinkedList<E>>> redoStack = new Stack<>();

    private UUID currentVersionNumber;

    private int size;

    private Node<E> first;

    private Node<E> last;

    public PersistentLinkedList() {
        size = 0;
        first = null;
        last = null;
        currentVersionNumber = UUID.randomUUID();
        currentVersion = new VersionNode<>(null, currentVersionNumber, null);
    }

    private PersistentLinkedList(Node<E> first, Node<E> last, int size, VersionNode<PersistentLinkedList<E>> version) {

    }

    @Override
    public PersistentCollection<E> add(E e) {
        return add(e, size - 1);
    }

    @Override
    public PersistentList<E> add(E e, int index) {
        Node<E>[] copiedNodes = copyNodesByIndex(index);
//        return new PersistentLinkedList<E>(copiedNodes[0], copiedNodes[copiedNodes.length-1], copiedNodes)
        throw new UnsupportedOperationException();
    }

    @Override
    public PersistentCollection<E> remove(Object e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersistentList<E> remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) return first.item;
        Node<E> wanted = first;
        for (int i = 1; i <= index; i++) {
            wanted = wanted.next;
        }
        return wanted.item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public UndoRedo undo() {
        redoStack.push(currentVersion);
        currentVersion = currentVersion.getPreviousVersion();
        size = currentVersion.getValue().size();
        currentVersionNumber = currentVersion.getVersionNumber();
        return this;
    }

    @Override
    public UndoRedo redo() {
        if (redoStack.empty()) {
            return this;
        } else {
            currentVersion = redoStack.pop();
            currentVersion = currentVersion.getPreviousVersion();
            size = currentVersion.getValue().size();
            currentVersionNumber = currentVersion.getVersionNumber();
            return this;
        }
    }

    private Node<E>[] copyNodesByIndex(int index) {
        Node<E>[] nodes = (Node<E>[])(new Object[index]);
        Node<E> temp = first;
        Node<E> elem = new Node<E>(null, temp.item, null);
        for (int i = 0; i < index; i++) {
            nodes[i] = elem;
            temp = temp.next;
            elem = new Node<E>(nodes[i], temp.item, null);
            nodes[i].next = elem;
        }
        return nodes;
    }

    private Node<E> copyNode(Node<E> node) {
        if (node == null) return null;
        return new Node<E>(node.prev, node.item, node.next);
    }

    private void unlink(Node<E> node) {
        node.next = null;
        node.item = null;
        node.prev = null;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

}
