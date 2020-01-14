package ru.nsu.fit.modern_programming.persistent_data_structures.trie;

public class PersistentBitTrie<T> extends BitTrie<T> {
    @Override
    public PersistentBitTrie<T> insert(int index, T value) {
        PersistentBitTrie<T> result = new PersistentBitTrie<>();
        result.shift = shift;
        result.root = new Object[WIDTH];

        System.arraycopy(root, 0, result.root, 0, WIDTH);

        Object[] prev = result.root;
        Object[] next;

        if (this.shift != 0) {
            while (index >= Math.pow(WIDTH, (result.shift + BITS) / BITS)) {
                result.root = new Object[WIDTH];
                result.root[0] = prev;
                result.shift += BITS;
                prev = result.root;
            }
            while (prev[(index >>> this.shift) & MASK] == null) {
                result.root[(index >>> this.shift) & MASK] = new Object[WIDTH];
            }
        } else {
            if ((prev[(index >>> this.shift) & MASK] != null) && ((index >>> this.shift) & MASK) != index) {
                result.shift += BITS;
                result.root = new Object[WIDTH];
                result.root[0] = prev;
                prev = result.root;
            }
        }

        for (int level = result.shift; level > 0; level -= BITS) {
            next = (Object[]) prev[(index >>> level) & MASK];
            if (next == null) {
                next = new Object[WIDTH];
                prev[(index >>> level) & MASK] = next;
                result.size = size + 1;
            }
            prev = next;
        }
        prev[index & MASK] = value;

        return result;
    }
}
