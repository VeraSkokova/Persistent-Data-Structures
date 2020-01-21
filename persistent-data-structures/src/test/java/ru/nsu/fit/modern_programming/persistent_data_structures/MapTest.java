package ru.nsu.fit.modern_programming.persistent_data_structures;

import org.junit.Assert;
import org.junit.Test;

public class MapTest {
    @Test
    public void testMap() {
        PersistentMap<Integer, String> map = new PersistentMap<>();

        map = map.put(0, "Zero");
        map = map.put(1, "First");

        Assert.assertTrue(map.containsKey(0));
        Assert.assertTrue(map.containsKey(1));

        Assert.assertEquals("Zero", map.get(0));
    }

    @Test
    public void testRewrite() {
        PersistentMap<Integer, String> map = new PersistentMap<>();

        map = map.put(0, "0");
        map = map.put(0, "00");

        System.out.println(map.get(0));
    }
}
