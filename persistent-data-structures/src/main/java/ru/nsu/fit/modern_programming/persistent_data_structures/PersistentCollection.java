package ru.nsu.fit.modern_programming.persistent_data_structures;

public interface PersistentCollection<E> extends Iterable<E> {

    boolean isEmpty();

    int size();

    PersistentCollection<E> add(E e);

    PersistentCollection<E> remove(Object e);

    boolean equals(Object o);

    int hashCode();

    Object[] toArray();

}
