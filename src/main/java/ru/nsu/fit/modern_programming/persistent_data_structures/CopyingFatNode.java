package ru.nsu.fit.modern_programming.persistent_data_structures;

public class CopyingFatNode<T> extends FatNode<T> {
    private int versionsCount;

    public CopyingFatNode(T value) {
        super(value);
    }
}
