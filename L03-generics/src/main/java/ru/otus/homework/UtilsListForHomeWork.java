package ru.otus.homework;

import java.util.List;

public class UtilsListForHomeWork {

    public List fillRandomNumbers(List<? super Integer> list, int size)
    {
        if (list == null) { return null; }

        for (int n = 0 ; n < size; n++) {
            list.add((int) (Math.random() * 10000));
        }

        return list;
    }

    public void printList(List<Integer> list, String prefix)
    {
        if (list == null || list.size() == 0) {
            System.out.println("List empty");
            return;
        }

        StringBuilder strPrint = new StringBuilder(prefix);
        for (int n = 0; n < list.size(); n++) {
            strPrint.append(list.get(n)).append(", ");
        }
        System.out.println(strPrint.substring(0, strPrint.length() - 2));
    }
}
