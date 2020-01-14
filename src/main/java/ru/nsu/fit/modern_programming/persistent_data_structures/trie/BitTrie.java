package ru.nsu.fit.modern_programming.persistent_data_structures.trie;

public class BitTrie<V> {
    protected static final int BITS = 5;
    protected static final int WIDTH = 1 << BITS; // 2^5 = 32
    protected static final int MASK = WIDTH - 1; // 31, or 0x1f

    // Array of objects. Can itself contain an array of objects.
    protected Object[] root = new Object[WIDTH];
    // BITS times (the depth of this trie minus one).
    protected int shift = 0;
    protected int size = 0;

    public BitTrie<V> insert(int index, V value) {
        Object[] prev = this.root;
        Object[] next;

        if (this.shift != 0) {
            while (index >= Math.pow(WIDTH, (this.shift + BITS) / BITS)) {
                this.root = new Object[WIDTH];
                this.root[0] = prev;
                this.shift += BITS;
                prev = this.root;
            }
            while (prev[(index >>> this.shift) & MASK] == null) {
                this.root[(index >>> this.shift) & MASK] = new Object[WIDTH];
            }
        } else {
            if ((prev[(index >>> this.shift) & MASK] != null) && ((index >>> this.shift) & MASK) != index) {
                this.shift += BITS;
                this.root = new Object[WIDTH];
                this.root[0] = prev;
                prev = this.root;
            }
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

        size++;

        return this;
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

    public int getSize() {
        return size;
    }
}

