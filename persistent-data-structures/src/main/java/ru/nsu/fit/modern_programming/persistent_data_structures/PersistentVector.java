package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.linked_list.PersistentDoublyLinkedList;
import ru.nsu.fit.modern_programming.persistent_data_structures.linked_list.PersistentList;
import ru.nsu.fit.modern_programming.persistent_data_structures.tree.Tree;
import ru.nsu.fit.modern_programming.persistent_data_structures.trie.PersistentBitTrie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PersistentVector<T> extends ArrayList<T> {
    private Tree<UUID, PersistentBitTrie<T>> versions;
    Tree<UUID, PersistentBitTrie<T>>.Node currentVersionNode;
    UUID currentVersion;

    public PersistentVector() {
    }

    public PersistentVector(Tree<UUID, PersistentBitTrie<T>>.Node currentVersionNode) {
        this.currentVersionNode = currentVersionNode;
    }

    @Override
    public int size() {
        return currentVersionNode.getValue().getSize();
    }

    @Override
    public T get(int index) {
        return (T) currentVersionNode.getValue().lookup(index);
    }

    @Override
    public boolean add(T t) {
        int lastIndex = versions == null ? 0 : currentVersionNode.getValue().getSize() - 1;
        add(lastIndex, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        PersistentBitTrie<T> latestVersion = currentVersionNode != null ? currentVersionNode.getValue() : null;
        if (latestVersion != null) {
            PersistentBitTrie<T> newVersion = latestVersion.insert(index, element);
            currentVersion = UUID.randomUUID();
            Tree<UUID, PersistentBitTrie<T>> temp = new Tree<>(currentVersion, newVersion);
            currentVersionNode.addChild(temp.getRoot());
            currentVersionNode = temp.getRoot();

            return;
        }
        latestVersion = new PersistentBitTrie<>();
        PersistentBitTrie<T> firstVersion = latestVersion.insert(index, element);
        currentVersion = UUID.randomUUID();
        versions = new Tree<>(currentVersion, firstVersion);
        currentVersionNode = versions.getRoot();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Object[] toArray() {
        return currentVersionNode.getValue().toArray(size());
    }

    public PersistentList<T> toPersistentList() {
        List<T> elements = new ArrayList<>();
        Collections.addAll(elements, (T[]) toArray());
        return new PersistentDoublyLinkedList<>(elements);
    }

    @Override
    public T remove(int index) {
        PersistentBitTrie<T> latestVersion = currentVersionNode != null ? currentVersionNode.getValue() : null;
        if (latestVersion != null) {
            PersistentBitTrie<T> newVersion = latestVersion.delete(index);
            updateVersion(newVersion);
            return latestVersion.lookup(index);
        }
        return null;
    }

    private void updateVersion(PersistentBitTrie<T> newVersion) {
        currentVersion = UUID.randomUUID();
        Tree<UUID, PersistentBitTrie<T>> temp = new Tree<>(currentVersion, newVersion);
        currentVersionNode.addChild(temp.getRoot());
        currentVersionNode = temp.getRoot();
    }
}

//Каскадные обновления - обновлять версию у объемлющей структуры
//НЕзависимый undo stack у версий
//Подумать про повороты дерева и копирование пути