package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.linked_list.PersistentDoublyLinkedList;
import ru.nsu.fit.modern_programming.persistent_data_structures.linked_list.PersistentList;
import ru.nsu.fit.modern_programming.persistent_data_structures.trie.PersistentBitTrie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersistentVector<T> implements PersistentCollection<T> {
    private PersistentBitTrie<T> persistentBitTrie;

    public PersistentVector() {
        persistentBitTrie = new PersistentBitTrie<>();
    }

    public PersistentVector(PersistentBitTrie<T> persistentBitTrie) {
        this.persistentBitTrie = persistentBitTrie;
    }

    @Override
    public int size() {
        return persistentBitTrie.getSize();
    }

    @Override
    public T get(int index) {
        return (T) persistentBitTrie.lookup(index);
    }

    @Override
    public PersistentVector<T> add(T t) {
        int lastIndex = persistentBitTrie.getSize() - 1;
        return add(lastIndex, t);
    }

    public PersistentVector<T> add(int index, T element) {
        PersistentBitTrie<T> newVersion = persistentBitTrie.insert(index, element);
        return new PersistentVector<>(newVersion);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Object[] toArray() {
        return persistentBitTrie.toArray(size());
    }

    public PersistentList<T> toPersistentList() {
        List<T> elements = new ArrayList<>();
        Collections.addAll(elements, (T[]) toArray());
        return new PersistentDoublyLinkedList<>(elements);
    }

    @Override
    public PersistentVector<T> remove(int index) {
        PersistentBitTrie<T> newVersion = persistentBitTrie.delete(index);
        return new PersistentVector<>(newVersion);
    }
}

//Каскадные обновления - обновлять версию у объемлющей структуры
//НЕзависимый undo stack у версий
//Подумать про повороты дерева и копирование пути