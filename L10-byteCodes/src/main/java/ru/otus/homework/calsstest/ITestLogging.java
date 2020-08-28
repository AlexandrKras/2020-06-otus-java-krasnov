package ru.otus.homework.calsstest;

import ru.otus.homework.annotations.Log;

public interface ITestLogging {
    void calculation(int param);
    void calculation(int param1, int param2);
    void calculation(int param1, int param2, String param3);
    void calculation(int param1, String param2, long param3);
}
