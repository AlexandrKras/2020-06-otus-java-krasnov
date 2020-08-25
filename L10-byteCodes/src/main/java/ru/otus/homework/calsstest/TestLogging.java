package ru.otus.homework.calsstest;

import ru.otus.homework.annotations.Log;

public class TestLogging implements ITestLogging {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("calculation(int param)");
        separate();
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("calculation(int param1, int param2)");
        separate();
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("calculation(int param1, int param2, String param3)");
        separate();
    }

    @Log
    @Override
    public void calculation(int param1, String param2, long param3) {
        System.out.println("calculation(int param1, String param2, long param3)");
        separate();
    }

    private void separate() {
        System.out.println("----------------------------------------------------");
    }
}
