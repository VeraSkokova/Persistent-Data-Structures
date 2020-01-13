package ru.nsu.fit.modern_programming.persistent_data_structures.trie;

public class PersistentBitTrie<T> extends BitTrie<T> {
    @Override
    public PersistentBitTrie<T> insert(int index, T value) {
        PersistentBitTrie<T> result = new PersistentBitTrie<>();
        result.shift = shift;
        result.root = this.root;

        Object[] prev = result.root;
        Object[] next;

        if (this.shift != 0) {
            while (prev[(index >>> this.shift) & MASK] == null) {
                result.root = new Object[WIDTH];
                result.root[0] = prev;
                result.shift += BITS;
                prev = result.root;
            }
        }

        for (int level = result.shift; level > 0; level -= BITS) {
            next = (Object[]) prev[(index >>> level) & MASK];
            if (next == null) {
                next = new Object[WIDTH];
                prev[(index >>> level) & MASK] = next;
            }
            prev = next;
        }
        prev[index & MASK] = value;

        result.size = size + 1;
        return result;
    }
}
