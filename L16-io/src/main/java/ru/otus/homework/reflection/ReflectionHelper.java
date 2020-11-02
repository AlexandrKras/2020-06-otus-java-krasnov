package ru.otus.homework.reflection;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static boolean isArray(Object obj) {
        return obj.getClass().isArray();
    }

    public static Field[] getFields(Object obj) {
        return obj.getClass().getDeclaredFields();
    }

    public static Object getFieldValue(Object obj, Field field) {
        Object returnObj = null;
        try {
            if (!field.canAccess(obj)) {
                field.setAccessible(true);
                returnObj = field.get(obj);
                field.setAccessible(false);
            } else {
                returnObj = field.get(obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return returnObj;
    }
}
