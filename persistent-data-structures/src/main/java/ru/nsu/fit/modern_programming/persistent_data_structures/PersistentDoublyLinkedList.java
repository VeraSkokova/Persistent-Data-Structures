package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.linked_list.PersistentList;

import java.util.*;

public class PersistentDoublyLinkedList<E> implements PersistentList<E> {

    private int size;

    private int version;

    private int[] maxAvailableVersion;
    
    private FatNode<E> first;

    private FatNode<E> last;

    public PersistentDoublyLinkedList() {
        maxAvailableVersion = new int[]{0};
    }

    public PersistentDoublyLinkedList(Collection<E> c) {
        E[] a = (E[]) c.toArray();

        int newSize = a.length;
        if (newSize == 0) return;

        version = 0;
        maxAvailableVersion = new int[]{0};
        size = newSize;

        Object[] curr = createSequence(a, 0);

        first = (FatNode<E>)curr[0];
        last = (FatNode<E>)curr[1];
    }

    private PersistentDoublyLinkedList(int[] maxAvailableVersion, int size, FatNode<E> first, FatNode<E> last) {
        this.version = maxAvailableVersion[0];
        this.maxAvailableVersion = maxAvailableVersion;
        this.size = size;
        this.first = first;
        this.last = last;
    }

    private PersistentDoublyLinkedList(int[] maxAvailableVersion, Collection<E> c) {
        this(c);
        this.version = maxAvailableVersion[0];
        this.maxAvailableVersion = maxAvailableVersion;
    }

    @Override
    public PersistentList<E> add(int index, E e) {
        maxAvailableVersion[0] = maxAvailableVersion[0] + 1;
        int newVersion = maxAvailableVersion[0];
        
        FatNode<E> newNode = new FatNode<>();
        newNode.addNode(new Node<E>(null, e, null), newVersion);

        FatNode<E> newFirst = null, newLast = null;
        if (index == 0) {
            if (first.versions.size() == 1) {
                Node<E> firstNode = first.getNode(version);
                first.addNode(new Node<E>(newNode, firstNode.item, firstNode.next), newVersion);
                newNode.getNode(newVersion).next = first;
                newFirst = newNode;
                newLast = last;
            } else {
                FatNode<E> nextNotFull = getNextNotFull(first);
                if (nextNotFull == null) {
                    Object[] arr = toArray();
                    Object[] newArr = new Object[arr.length + 1];
                    newArr[0] = e;
                    System.arraycopy(arr, 0, newArr, 1, arr.length);
                    return new PersistentDoublyLinkedList<E>(maxAvailableVersion, (Collection)Arrays.asList(newArr));
                } else {
                    Object[] curr = copy(first, nextNotFull.getNode(version).prev, newVersion);
                    FatNode<E> first = (FatNode<E>) curr[0];
                    FatNode<E> last = (FatNode<E>) curr[1];
                    newNode.getNode(newVersion).next = first;
                    first.getNode(newVersion).prev = newNode;
                    last.getNode(newVersion).next = nextNotFull;
                    nextNotFull.addNode(new Node<E>(last, nextNotFull.getNode(version).item, nextNotFull.getNode(version).next), newVersion);
                    newFirst = newNode;
                    newLast = last;
                }
            }
        }
        else if (index == size) {
            if (last.versions.size() == 1) {
                Node<E> lastNode = last.getNode(version);
                last.addNode(new Node<E>(lastNode.prev, lastNode.item, newNode), newVersion);
                newNode.getNode(newVersion).prev = last;
                newFirst = first;
                newLast = newNode;
            } else {
                FatNode<E> prevNotFull = getPrevNotFull(last);
                if (prevNotFull == null) {
                    Object[] arr = toArray();
                    Object[] newArr = new Object[arr.length + 1];
                    newArr[newArr.length - 1] = e;
                    System.arraycopy(arr, 0, newArr, 0, arr.length);
                    return new PersistentDoublyLinkedList<E>(maxAvailableVersion, (Collection)Arrays.asList(newArr));
                } else {
                    Object[] curr = copy(prevNotFull.getNode(version).next, last, newVersion);
                    FatNode<E> first = (FatNode<E>) curr[0];
                    FatNode<E> last = (FatNode<E>) curr[1];
                    newNode.getNode(newVersion).prev = last;
                    first.getNode(newVersion).prev = prevNotFull;
                    last.getNode(newVersion).next = newNode;
                    prevNotFull.addNode(new Node<E>(prevNotFull.getNode(version).prev, prevNotFull.getNode(version).item, first), newVersion);
                    newFirst = first;
                    newLast = newNode;
                }
            }
        }
        else {
            FatNode<E> node = getNode(index);
            FatNode<E> nextNotFull = getNextNotFull(node);
            if (nextNotFull == null) {
                Object[] curr = copy(node, last, newVersion);
                FatNode<E> first = (FatNode<E>) curr[0];
                FatNode<E> last = (FatNode<E>) curr[1];
                newNode.getNode(newVersion).next = first;
                first.getNode(newVersion).prev = newNode;
                newLast = last;
            }
            else {
                if (nextNotFull == node) {
                    node.addNode(new Node<>(newNode, node.getNode(version).item, node.getNode(version).next), newVersion);
                    newNode.getNode(newVersion).next = node;
                }
                else {
                    Object[] curr = copy(node, nextNotFull.getNode(version).prev, newVersion);
                    FatNode<E> first = (FatNode<E>) curr[0];
                    FatNode<E> last = (FatNode<E>) curr[1];
                    newNode.getNode(newVersion).next = first;
                    first.getNode(newVersion).prev = newNode;
                    last.getNode(newVersion).next = nextNotFull;
                    nextNotFull.addNode(new Node<>(last, nextNotFull.getNode(version).item, nextNotFull.getNode(version).next), newVersion);
                }
                newLast = last;
            }

            FatNode<E> prevNotFull = getPrevNotFull(node.getNode(version).prev);
            if (prevNotFull == null) {
                Object[] curr = copy(first, node.getNode(version).prev, newVersion);
                FatNode<E> first = (FatNode<E>) curr[0];
                FatNode<E> last = (FatNode<E>) curr[1];
                newNode.getNode(newVersion).prev = last;
                last.getNode(newVersion).next = newNode;
                newFirst = first;
            }
            else {
                if (prevNotFull == node.getNode(version).prev) {
                    FatNode<E> prev = node.getNode(version).prev;
                    prev.addNode(new Node<>(prev.getNode(version).prev, prev.getNode(version).item, newNode), newVersion);
                    newNode.getNode(newVersion).prev = prev;
                }
                else {
                    Object[] curr = copy(prevNotFull.getNode(version).next, node.getNode(version).prev, newVersion);
                    FatNode<E> first = (FatNode<E>) curr[0];
                    FatNode<E> last = (FatNode<E>) curr[1];
                    newNode.getNode(newVersion).prev = last;
                    first.getNode(newVersion).prev = prevNotFull;
                    last.getNode(newVersion).next = newNode;
                    prevNotFull.addNode(new Node<>(prevNotFull.getNode(version).prev, prevNotFull.getNode(version).item, first), newVersion);
                }
                newFirst = first;
            }

        }

        return new PersistentDoublyLinkedList<E>(maxAvailableVersion, size + 1, newFirst, newLast);
    }

    @Override
    public PersistentCollection<E> add(E e) {
        return add(size, e);
    }

    public PersistentCollection<E> addFirst(E e) {
        return add(0, e);
    }

    @Override
    public PersistentList<E> remove(int index) {
        maxAvailableVersion[0] = maxAvailableVersion[0] + 1;
        int newVersion = maxAvailableVersion[0];
        
        FatNode<E> newFirst = null, newLast = null, leftNode = null, rightNode = null;
        FatNode<E> node = getNode(index);

        FatNode<E> nextNotFull = getNextNotFull(node.getNode(version).next);

        if (nextNotFull == null) {
            Object[] curr = copy(node.getNode(version).next, last, newVersion);
            rightNode = (FatNode<E>) curr[0];
            newLast = (FatNode<E>) curr[1];
        }
        else {
            if (nextNotFull == node.getNode(version).next) {
                rightNode = nextNotFull;
            }
            else {
                Object[] curr = copy(node.getNode(version).next, nextNotFull.getNode(version).prev, newVersion);
                FatNode<E> first = (FatNode<E>) curr[0];
                FatNode<E> last = (FatNode<E>) curr[1];
                last.getNode(newVersion).next = nextNotFull;
                nextNotFull.addNode(new Node<>(last, nextNotFull.getNode(version).item, nextNotFull.getNode(version).next), newVersion);
                rightNode = first;
            }
            newLast = last;
        }

        FatNode<E> prevNotFull = getPrevNotFull(node.getNode(version).prev);

        if (prevNotFull == null) {
            Object[] curr = copy(first, node.getNode(version).prev, newVersion);
            leftNode = (FatNode<E>) curr[1];
            newFirst = (FatNode<E>) curr[0];
        }
        else {
            if (prevNotFull == node.getNode(version).prev) {
                leftNode = prevNotFull;
            }
            else {
                Object[] curr = copy(prevNotFull.getNode(version).next, node.getNode(version).prev, newVersion);
                FatNode<E> first = (FatNode<E>) curr[0];
                FatNode<E> last = (FatNode<E>) curr[1];
                first.getNode(newVersion).prev = prevNotFull;
                prevNotFull.addNode(new Node<>(prevNotFull.getNode(version).prev, prevNotFull.getNode(version).item, first), newVersion);
                leftNode = last;
            }
            newFirst = first;
        }

        if (!leftNode.versions.containsKey(newVersion)) {
            leftNode.addNode(new Node<>(leftNode.getNode(version).prev, leftNode.getNode(version).item, rightNode), newVersion);
        }
        else {
            leftNode.getNode(newVersion).next = rightNode;
        }

        if (!rightNode.versions.containsKey(newVersion)) {
            rightNode.addNode(new Node<>(leftNode, rightNode.getNode(version).item, rightNode.getNode(version).next), newVersion);
        }
        else {
            rightNode.getNode(newVersion).prev = leftNode;
        }

        return new PersistentDoublyLinkedList<E>(maxAvailableVersion, size - 1, newFirst, newLast);
    }

    @Override
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).getNode(version).item;
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

    private Object[] toArray(FatNode<E> start, FatNode<E> end) {
        List<E> items = new ArrayList<>();
        FatNode<E> curr = start;
        items.add(curr.getNode(version).item);

        while (curr != end) {
            curr = curr.getNode(version).next;
            items.add(curr.getNode(version).item);
        }

        return items.toArray();
    }

    private FatNode<E> getNode(int index) {
        FatNode<E> found;
        if (index < size / 2) {
            found = first;
            for (int i = 0; i < index; i++) {
                found = found.getNode(version).next;
            }
        } else {
            found = last;
            for (int i = size - 1 - index; i > 0; i--) {
                found = found.getNode(version).prev;
            }
        }
        return found;
    }

    private FatNode<E> getNextNotFull(FatNode<E> start) {
        FatNode<E> node = start;
        while (node.versions.size() != 1) {
            node = node.getNode(version).next;
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    private FatNode<E> getPrevNotFull(FatNode<E> start) {
        FatNode<E> node = start;
        while (node.versions.size() != 1) {
            node = node.getNode(version).prev;
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    private Object[] copy(FatNode<E> start, FatNode<E> end, int version) {
        return createSequence(toArray(start, end), version);
    }

    private Object[] createSequence(Object[] items, int version) {
        FatNode<E> curr = null, first = null, last;

        for(Object o : items) {
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

        Object[] sequence = new Object[2];
        sequence[0] = first;
        sequence[1] = last;

        return sequence;
    }

    private static class FatNode<E> {

        private Map<Integer, Node<E>> versions = new HashMap<>();

        Node<E> getNode(int version) {
            return versions.getOrDefault(version, getLastAvailableNode(version));
        }

        void addNode(Node<E> node, int version) {
            versions.put(version, node);
        }

        private Node<E> getLastAvailableNode(int version) {
            int[] availableVersion = new int[1];
            versions.forEach((k, v) -> {
                if (k <= version) {
                    availableVersion[0] = k;
                }
            });

            return versions.get(availableVersion[0]);
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
