package ru.nsu.fit.modern_programming.persistent_data_structures;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        PersistentDoublyLinkedList<Double> list1 = new PersistentDoublyLinkedList<>();
        System.out.println(list1);
        PersistentDoublyLinkedList<Double> list2 = new PersistentDoublyLinkedList<Double>(List.of(1.2, 2.6, 2.912, -3.56, 130.,-78.55, 0.32, -0.23, 10.22303, -228.7));
        Object[] array = list2.toArray();
        array[0] = -3.14;
        System.out.println(list2);
        System.out.println(Arrays.toString(list2.toArray()));
        System.out.println(Arrays.toString(array));
        System.out.println(list2.get(0));
        System.out.println(list2.get(1));
        System.out.println(list2.get(2));
        System.out.println(list2.get(3));
        System.out.println(list2.get(4));
        System.out.println(list2.get(5));
//        System.out.println(list2.get(6)); // exception
        PersistentDoublyLinkedList<Double> list3 = (PersistentDoublyLinkedList<Double>) list2.add(4, 7.8);
        System.out.println("list3: " + list3 + " size: " + list3.size());
        PersistentDoublyLinkedList<Double> list4 = (PersistentDoublyLinkedList<Double>) list2.add(4, -22.3);
        System.out.println("list4: " + list4 + " size: " + list4.size());
        PersistentDoublyLinkedList<Double> list5 = (PersistentDoublyLinkedList<Double>) list4.add(5, 23.1);
        System.out.println("list5: " + list5 + " size: " + list5.size());
        PersistentDoublyLinkedList<Double> list6 = (PersistentDoublyLinkedList<Double>) list5.remove(5);
        System.out.println("list6: " + list6 + " size: " + list6.size());
        PersistentDoublyLinkedList<Double> list7 = (PersistentDoublyLinkedList<Double>) list5.remove(5);
        System.out.println("list7: " + list7 + " size: " + list7.size());
        System.out.println("list3: " + list3 + " size: " + list3.size());
        System.out.println("list4: " + list4 + " size: " + list4.size());
        System.out.println("list5: " + list5 + " size: " + list5.size());
        System.out.println("list6: " + list6 + " size: " + list6.size());
        System.out.println("list7: " + list7 + " size: " + list7.size());

    }
}
