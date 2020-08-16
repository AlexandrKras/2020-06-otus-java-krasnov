package ru.otus.homework.tests;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;
import ru.otus.homework.calssfortest.MathFunc;
import ru.otus.homework.fortest.AHomeWorkTest;

public class MathFuncTest extends AHomeWorkTest {
    private MathFunc mathFunc = null;

    @Before
    @Override
    public void setUp() {
        mathFunc = new MathFunc();
    }

    @After
    @Override
    public void tearDown() {
        mathFunc = null;
    }

    @Test
    public void plusTest() throws Exception {
        assertLong(mathFunc.plus(1, 2), 3);
        assertLong(mathFunc.plus(10, 12), 22);
        assertLong(mathFunc.plus(101, 112), 213);
    }

    @Test
    public void minusTest() throws Exception {
        assertLong(mathFunc.minus(10, 2), 8);
        assertLong(mathFunc.minus(103, 12), 91);
        assertLong(mathFunc.minus(101, 13), 88);
    }

    @Test
    public void multiplyTest() throws Exception {
        assertLong(mathFunc.multiply(1, 1), 1);
        assertLong(mathFunc.multiply(10, 10), 100);
        assertLong(mathFunc.multiply(100, 100), 10000);
    }

    @Test
    public void errorTest() throws Exception {
        assertLong(mathFunc.multiply(100, 100), 1000);
    }
}
