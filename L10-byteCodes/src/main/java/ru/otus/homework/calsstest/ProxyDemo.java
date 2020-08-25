package ru.otus.homework.calsstest;

public class ProxyDemo {
    public static void main(String[] args) {
        ITestLogging testLogging = new Ioc().createMyClass();
        testLogging.calculation(6);
        testLogging.calculation(15, 55);
        testLogging.calculation(100, 200, "500");
        testLogging.calculation(6, "10", 50l);
    }
}

