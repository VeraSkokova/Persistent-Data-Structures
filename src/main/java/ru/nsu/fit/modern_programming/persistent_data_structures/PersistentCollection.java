package ru.nsu.fit.modern_programming.persistent_data_structures;

import java.io.IOException;

public interface PersistentCollection<E> {

    boolean isEmpty();

    int size();

    PersistentCollection<E> add(E e);

    boolean equals(Object o);

    int hashCode();

    Object[] toArray();

}
