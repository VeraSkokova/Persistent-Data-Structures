package ru.nsu.fit.modern_programming.persistent_data_structures;

import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.AvlTree;
import ru.nsu.fit.modern_programming.persistent_data_structures.avl_tree.PersistentAvlTree;

public class Main {
    public static void main(String[] args) {
        AvlTree<Integer, Integer> tree = new AvlTree<>(2, 2);
        tree = tree.insert(3, 3);
        tree = tree.insert(4, 4);
        tree = tree.insert(5, 5);
        tree = tree.insert(7, 7);
        tree = tree.insert(8, 8);
        tree = tree.insert(9, 9);
        tree = tree.insert(1, 1);
        tree = tree.insert(10, 10);

        System.out.println(tree.getHeight());
        System.out.println(tree.getKey());
        System.out.println(tree.find(8));

        AvlTree<Integer, Integer> persistentTree = new PersistentAvlTree<>(1, 1);
        AvlTree<Integer, Integer> secondTree = persistentTree.insert(2, 2);
        AvlTree<Integer, Integer> thirdTree = secondTree.insert(0, 0);
        System.out.println(thirdTree.getHeight());

        PersistentVector<Integer> vector = new PersistentVector<>();

        for (int i = 0; i < 1024 * 1024 + 1; i++) {
            if (i == 1024) {
                System.out.println("bug");
            }
            vector.add(i, i);
        }

        for (int i = 0; i < 64; i++) {
            System.out.println("vector[" + i + "] = " + vector.get(i));
        }

        System.out.println("The end of program");
    }
}
