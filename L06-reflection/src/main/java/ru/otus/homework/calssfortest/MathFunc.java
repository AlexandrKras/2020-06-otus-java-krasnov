package ru.otus.homework.calssfortest;

public class MathFunc {

    public long plus(int num1, int num2) {
        return num1 + num2;
    }

    public long minus(int num1, int num2) {
        return num1 - num2;
    }

    public long multiply(int num1, int num2) {
        return num1 * num2;
    }

    public long factorial(int number) {
        if (number < 0)
            throw new IllegalArgumentException();

        long result = 1;
        if (number > 1) {
            for (int i = 1; i <= number; i++)
                result = result * i;
        }

        return result;
    }
}
