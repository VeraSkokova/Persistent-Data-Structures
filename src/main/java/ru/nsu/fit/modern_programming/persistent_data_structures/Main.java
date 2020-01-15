package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.AvlTree;
import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.PersistentAvlTree;
import ru.nsu.fit.modern_programming.persistent_data_structures.linked_list.PersistentDoublyLinkedList;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        AvlTree<Integer, Integer> tree = new AvlTree<>(2, 2);
//        tree = tree.insert(3, 3);
//        tree = tree.insert(4, 4);
//        tree = tree.insert(5, 5);
//        tree = tree.insert(7, 7);
//        tree = tree.insert(8, 8);
//        tree = tree.insert(9, 9);
//        tree = tree.insert(1, 1);
//        tree = tree.insert(10, 10);
//
//        System.out.println(tree.getHeight());
//        System.out.println(tree.getKey());
//        System.out.println(tree.find(8));
//
//        AvlTree<Integer, Integer> persistentTree = new PersistentAvlTree<>(1, 1);
//        AvlTree<Integer, Integer> secondTree = persistentTree.insert(2, 2);
//        AvlTree<Integer, Integer> thirdTree = secondTree.insert(0, 0);
//        System.out.println(thirdTree.getHeight());

        PersistentDoublyLinkedList<Double> list1 = new PersistentDoublyLinkedList<>();
        System.out.println(list1);
        PersistentDoublyLinkedList<Double> list2 = new PersistentDoublyLinkedList<Double>(List.of(1.2, 2.6, 2.912));
        Object[] array = list2.toArray();
        array[0] = -3.14;
        System.out.println(list2);
        System.out.println(Arrays.toString(list2.toArray()));
        System.out.println(Arrays.toString(array));
    }
}
