package ru.nsu.fit.modern_programming.persistent_data_structures;

public interface PersistentCollection<E> {

    boolean isEmpty();

    int size();

    PersistentCollection<E> add(E e);

    boolean equals(Object o);

    int hashCode();

    Object[] toArray();

    E get(int index);

    PersistentCollection<E> remove (int index);
}
