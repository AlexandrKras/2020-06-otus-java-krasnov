package ru.otus.homework.fortest;

public abstract class AHomeWorkTest implements IHomeWorkTest {

    public abstract void setUp();
    public abstract void tearDown();

    @Override
    public final void assertLong(long num1, long num2) throws AssertionError {
        if (num1 != num2)
            throw new AssertionError();
    }

    @Override
    public final void assertString(String str1, String str2) throws AssertionError {
        if (!str1.equals(str2))
            throw new AssertionError();
    }
}
