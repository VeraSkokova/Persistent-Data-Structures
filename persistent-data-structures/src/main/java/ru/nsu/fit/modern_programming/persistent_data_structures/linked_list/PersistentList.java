package ru.nsu.fit.modern_programming.persistent_data_structures.linked_list;

import ru.nsu.fit.modern_programming.persistent_data_structures.PersistentCollection;

public interface PersistentList<E> extends PersistentCollection<E> {

    PersistentList<E> add(E e, int index);

    PersistentList<E> remove(int index);

    E get(int index);

}
