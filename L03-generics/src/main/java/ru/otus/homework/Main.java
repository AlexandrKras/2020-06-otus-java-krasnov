package ru.otus.homework;

import ru.otus.generics.DIYArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> myList = new DIYArrayList<>();
        
        UtilsListForHomeWork util = new UtilsListForHomeWork();

        util.fillRandomNumbers(myList, 30);
        util.printList(myList, "     Исходный список: ");
        Collections.sort(myList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) { return 1; }
                if (o1 < o2) { return -1; }

                return 0;

            }
        });
        util.printList(myList, "Сортированный список: ");

        List<Integer> forCopyList = new DIYArrayList<>(30);
        Collections.copy(forCopyList, myList);
        util.printList(forCopyList, "Скопированный список: ");

        Collections.addAll(myList, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 10000);
        util.printList(myList, "Список с добавлеными: ");
    }
}
