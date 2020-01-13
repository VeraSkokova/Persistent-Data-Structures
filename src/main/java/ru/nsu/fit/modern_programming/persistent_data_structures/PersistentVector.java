package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.trie.PersistentBitTrie;

import java.util.ArrayList;
import java.util.List;

public class PersistentVector<T> extends ArrayList<T> {
    private List<PersistentBitTrie<T>> versions = new ArrayList<>();
    private int currentVersion = 0;

    @Override
    public int size() {
        return versions.get(currentVersion).getSize();
    }

    @Override
    public T get(int index) {
        return (T) versions.get(currentVersion).lookup(index);
    }

    @Override
    public boolean add(T t) {
        int lastIndex = versions.isEmpty() ? 0 : versions.get(currentVersion - 1).getSize() - 1;
        add(lastIndex, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        PersistentBitTrie<T> latestVersion = currentVersion != 0 ? versions.get(currentVersion - 1) : null;
        if (latestVersion != null) {
            PersistentBitTrie<T> newVersion = latestVersion.insert(index, element);
            versions.add(newVersion);
            currentVersion++;
            return;
        }
        latestVersion = new PersistentBitTrie<>();
        PersistentBitTrie<T> firstVersion = latestVersion.insert(index, element);
        versions.add(firstVersion);
        currentVersion++;
    }
}
