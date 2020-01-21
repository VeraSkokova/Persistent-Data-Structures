package ru.nsu.fit.modern_programming.persistent_data_structures;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {
    @Test
    public void testVector() {
        PersistentVector<Integer> vector = new PersistentVector<>();

        for (int i = 0; i < 1024 * 1024 + 1; i++) {
            vector.add(i, i);
        }

        for (int i = 0; i < 1024 * 1024 + 1; i++) {
            Assert.assertEquals(i, vector.get(i).intValue());
        }
    }

    @Test
    public void testUndo() {
        UndoRedoPersistentVector<Integer> vector = new UndoRedoPersistentVector<>();

        vector.add(0, 0);
        vector.add(0, 1);

        vector.undo();

        Assert.assertEquals(0, vector.get(0).intValue());
    }

    @Test
    public void testRedo() {
        UndoRedoPersistentVector<Integer> vector = new UndoRedoPersistentVector<>();

        vector.add(0, 0);
        vector.add(0, 1);

        vector.undo();
        vector.redo();

        Assert.assertEquals(1, vector.get(0).intValue());
    }

    @Test
    public void testDelete() {
        PersistentVector<Integer> vector = new PersistentVector<>();

        for (int i = 0; i < 128; i++) {
            vector.add(i, i);
        }

        vector.remove(69);

        for (int i = 0; i < 69; i++) {
            Assert.assertEquals(i, vector.get(i).intValue());
        }
        for (int i = 69; i < 127; i++) {
            Assert.assertEquals(i + 1, vector.get(i).intValue());
        }

        Assert.assertNull(vector.get(127));
    }
}
