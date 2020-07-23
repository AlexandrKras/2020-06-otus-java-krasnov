package ru.otus;

import java.util.Optional;

public class HelloOtus {
    public static void main(String args[]) {
        HelloOtus helloOtus = new HelloOtus();

        Integer invalidInput = null;
        Optional<Integer> a =  Optional.of(100);
        Optional<Integer> b =  Optional.of(Integer.valueOf(10));
        System.out.println(helloOtus.sum(a,b));
    }

    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        return a.get() + b.get();
    }
}
