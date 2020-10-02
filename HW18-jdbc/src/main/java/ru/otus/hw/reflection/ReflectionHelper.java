package ru.otus.hw.reflection;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static boolean isArray(Object obj) {
        return obj.getClass().isArray();
    }

    public static Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
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

    public static void setFieldValue(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
