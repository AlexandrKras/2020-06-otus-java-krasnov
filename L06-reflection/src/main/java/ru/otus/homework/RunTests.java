package ru.otus.homework;

import ru.otus.homework.reflection.TestHandler;
import ru.otus.homework.tests.MathFuncTest;
import ru.otus.homework.tests.StringUtilTest;

public class RunTests {
    public static void main(String[] args) {
        TestHandler handler = new TestHandler();
        try {
            handler.run(MathFuncTest.class);
            handler.run(StringUtilTest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (handler != null)
                handler.printResault();
        }
    }
}
