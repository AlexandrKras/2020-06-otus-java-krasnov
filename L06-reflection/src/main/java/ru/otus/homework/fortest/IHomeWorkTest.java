package ru.otus.homework.fortest;

public interface IHomeWorkTest {
    void assertLong(long num1, long num2) throws AssertionError;

    void assertString(String str1, String str2) throws AssertionError;
}
