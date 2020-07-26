package ru.otus.homework;

import ru.otus.generics.DIYArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> myList = new DIYArrayList<>();
        String separator = "-------------------------------------------------------";

        UtilsListForHomeWork util = new UtilsListForHomeWork();
        System.out.println(separator + " Пример для 30 элемнтов " + separator);
        printForExample(myList, util, 30);
        System.out.println(separator + " Пример для 2000 элемнтов " + separator);
        printForExample(myList, util, 2000);

    }

    private static void printForExample(List<Integer> list, UtilsListForHomeWork util, int quantity) {
        util.fillRandomNumbers(list, quantity);
        util.printList(list, "     Исходный список: ");
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) { return 1; }
                if (o1 < o2) { return -1; }

                return 0;

            }
        });
        util.printList(list, "Сортированный список: ");

        List<Integer> forCopyList = new DIYArrayList<>(list.size());
        Collections.copy(forCopyList, list);
        util.printList(forCopyList, "Скопированный список: ");

        Collections.addAll(list, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 10000);
        util.printList(list, "Список с добавлеными: ");
    }
}
