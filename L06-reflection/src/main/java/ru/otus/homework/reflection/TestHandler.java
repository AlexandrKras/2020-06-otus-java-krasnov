package ru.otus.homework.reflection;

import java.lang.reflect.Method;
import java.util.List;

public class TestHandler {

    private int all = 0;
    private int successfully = 0;
    private int failed = 0;

    public void run(Class<?> clazz) {
        if (!TestHelper.isClassTest(clazz)) {
            return;
        }

        all += TestHelper.quantityTests(clazz);
        List<Method> methodsTest = TestHelper.getMethodsTest(clazz);
        if (methodsTest == null || methodsTest.size() == 0) {
            return;
        }
        Method methodSetUp = TestHelper.getMethodsSetUp(clazz);
        Method methodTearDown = TestHelper.getMethodTearDown(clazz);
        for (Method method : methodsTest) {
            try {
                Object obj = clazz.getDeclaredConstructor().newInstance();
                if (methodSetUp != null) {
                    methodSetUp.invoke(obj);
                }

                method.invoke(obj);

                if (methodTearDown != null) {
                    methodTearDown.invoke(obj);
                }
                successfully++;
            } catch (Exception e) {
                e.printStackTrace();
                failed++;
            }
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
