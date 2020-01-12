package ru.nsu.fit.modern_programming.persistent_data_structures.trie;

public class BitTrie<V> {
    private static final int BITS = 5;
    private static final int WIDTH = 1 << BITS; // 2^5 = 32
    private static final int MASK = WIDTH - 1; // 31, or 0x1f

    // Array of objects. Can itself contain an array of objects.
    private Object[] root;
    // BITS times (the depth of this trie minus one).
    private int shift;

    public void insert(int index, V value) {
        Object[] prev = this.root;
        Object[] next;

        while (prev[(index >>> this.shift) & MASK] == null) {
            this.root = new Object[WIDTH];
            this.root[0] = prev;
            this.shift += BITS;
            prev = this.root;
        }

        for (int level = this.shift; level > 0; level -= BITS) {
            next = (Object[]) prev[(index >>> level) & MASK];
            if (next == null) {
                next = new Object[WIDTH];
                prev[(index >>> level) & MASK] = next;
            }
            prev = next;
        }
        prev[index & MASK] = value;
    }

    public Object lookup(int index) {
        Object[] node = this.root;

        // perform branching on internal nodes here
        for (int level = this.shift; level > 0; level -= BITS) {
            node = (Object[]) node[(index >>> level) & MASK];
            // If node may not exist, check if it is null here
            if (node == null) {
                return null;
            }
        }

        // Last element is the value we want to lookup, return it.
        return node[index & MASK];
    }
}

