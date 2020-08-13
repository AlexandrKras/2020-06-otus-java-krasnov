package ru.otus.homework.reflection;

import ru.otus.reflection.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TestHandler {

    private int all = 0;
    private int successfully = 0;
    private int failed = 0;

    public void run(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!TestHelper.isClassTest(clazz)) {
            return;
        }

        all += TestHelper.quantityTests(clazz);

        Object obj = clazz.getDeclaredConstructor().newInstance();
        Method methodSetUp = TestHelper.getMethodsSetUp(clazz);
        if (methodSetUp != null) {
            methodSetUp.invoke(obj);
        }

        List<Method> methodsTest = TestHelper.getMethodsTest(clazz);
        if (methodsTest != null && methodsTest.size() > 0) {
            for (Method method : methodsTest) {
                try {
                    method.invoke(obj);
                    successfully++;
                } catch (Exception e) {
                    e.printStackTrace();
                    failed++;
                }
            }
        }

        Method methodTearDown = TestHelper.getMethodTearDown(clazz);
        if (methodTearDown != null) {
            methodTearDown.invoke(obj);
        }
    }

    public void printResault() {
        System.out.print("Всего тестов: " + all);
        System.out.print("\t");
        System.out.print("Успешно : " + successfully);
        System.out.print("\t");
        System.out.println("Провалено : " + failed);
    }
}
