package ru.nsu.fit.modern_programming.persistent_data_structures.linked_list;

import ru.nsu.fit.modern_programming.persistent_data_structures.PersistentCollection;

public interface PersistentList<E> extends PersistentCollection<E> {

    PersistentList<E> add(int index, E e);

    PersistentList<E> remove(int index);

    E get(int index);

}
