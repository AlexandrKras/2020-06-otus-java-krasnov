package ru.otus.homework.reflection;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static int quantityTests(Class<?> clazz) {
        return getMethods(clazz, Test.class).size();
    }

    public static boolean isClassTest(Class<?> clazz) {
        return quantityTests(clazz) > 0;
    }

    public static Method getMethodsSetUp(Class<?> clazz) {
        List<Method> methodsIsAnnotationBefore = getMethods(clazz, Before.class);
        if (methodsIsAnnotationBefore.size() == 0) {
            return null;
        }
        if (methodsIsAnnotationBefore.size() > 1) {
            throw new RuntimeException("only one method can be annotated Before");
        }

        return methodsIsAnnotationBefore.get(0);
    }

    public static Method getMethodTearDown(Class<?> clazz) {
        List<Method> methodsIsAnnotationAfter = getMethods(clazz, After.class);
        if (methodsIsAnnotationAfter.size() == 0) {
            return null;
        }
        if (methodsIsAnnotationAfter.size() > 1) {
            throw new RuntimeException("only one method can be annotated After");
        }

        return methodsIsAnnotationAfter.get(0);
    }

    public static List getMethodsTest(Class<?> clazz) {
        List<Method> methodsIsAnnotationTest = getMethods(clazz, Test.class);
        if (methodsIsAnnotationTest.size() == 0) {
            return null;
        }

        return methodsIsAnnotationTest;
    }

    private static List getMethods(Class<?> clazz, Class<? extends Annotation> classAnnotation) {
        Method[] methods = clazz.getDeclaredMethods();

        List<Method> methodsIsAnnotation = new ArrayList();
        for (Method method : methods) {
            if (method.isAnnotationPresent(classAnnotation)) {
                methodsIsAnnotation.add(method);
            }
        }

        return methodsIsAnnotation;
    }
}
