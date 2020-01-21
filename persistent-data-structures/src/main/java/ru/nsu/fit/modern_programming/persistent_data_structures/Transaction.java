package ru.nsu.fit.modern_programming.persistent_data_structures;

public interface Transaction<V> {
    V startTransaction();

    V endTransaction(V transaction);
}
