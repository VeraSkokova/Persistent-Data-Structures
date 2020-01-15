package ru.nsu.fit.modern_programming.persistent_data_structures.linked_list;

import ru.nsu.fit.modern_programming.persistent_data_structures.PersistentCollection;

import java.util.*;

public class PersistentDoublyLinkedList<E> implements PersistentList<E> {

    private int size;

    private int version;

    private FatNode<E> first;

    private FatNode<E> last;

    public PersistentDoublyLinkedList() {
    }

    public PersistentDoublyLinkedList(Collection<E> c) {
        E[] a = (E[]) c.toArray();

        int newSize = a.length;
        if (newSize == 0) return;

        version = 0;
        size = newSize;

        FatNode<E> curr = null;

        for(Object o : a) {
            E e = (E) o;
            if (curr == null) {
                curr = new FatNode<>();
                curr.addNode(new Node<E>(null, e, null), version);
                first = curr;
            } else {
                curr.getNode(version).next = new FatNode<>();
                curr.getNode(version).next.addNode(new Node<E>(curr, e, null), version);
                curr = curr.getNode(version).next;
            }
        }

        last = curr;

    }

    @Override
    public PersistentList<E> add(E e, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersistentCollection<E> add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersistentList<E> remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersistentCollection<E> remove(Object e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        FatNode<E> found;
        if (index < size / 2) {
            found = first;
            for (int i = 0; i <= index; i++) {
                found = found.getNode(version).next;
            }
        } else {
            found = last;
            for (int i = size - 1 - index; i > 0; i--) {
                found = found.getNode(version).prev;
            }
        }
        return found.getNode(version).item;
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
        Object[] array = new Object[size];

        FatNode<E> e = first;
        for (int i = 0; i < size; i++) {
            Node<E> n = e.getNode(version);
            array[i] = n.item;
            e = n.next;
        }

        return array;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    private void unlink(Node<E> node) {
        node.next = null;
        node.item = null;
        node.prev = null;
    }

    private static class FatNode<E> {

        private Map<Integer, Node<E>> versions = new HashMap<>();

        public Node<E> getNode(int version) {
            return versions.getOrDefault(version, getLastNode());
        }

        public void addNode(Node<E> node, int version) {
            versions.put(version, node);
        }

        private Node<E> getLastNode() {
            Object[] arr = versions.keySet().toArray();
            if (arr.length == 1) {
                return versions.get((Integer) arr[0]);
            }
            return versions.get(Math.max((Integer) arr[0], (Integer) arr[1]));
        }

    }

    private static class Node<E> {
        E item;
        FatNode<E> next;
        FatNode<E> prev;

        Node(FatNode<E> prev, E element, FatNode<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        FatNode<E> e = first;
        for (int i = 0; i < size; i++) {
            Node<E> n = e.getNode(version);
            str.append(n.item.toString()).append(", ");
            e = n.next;
        }
        str.append("]");

        return str.toString().replace(", ]", "]");
    }
}
