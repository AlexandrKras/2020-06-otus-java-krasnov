package ru.otus.jdbc.mapper.implementation;

import ru.otus.hw.annotations.Id;
import ru.otus.jdbc.exception.JdbcMapperImplException;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.hw.reflection.ReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private final Class<T> clazz;
    private final Field[] fields;
    private final List<Field> fieldsWithoutId = new ArrayList<>();
    private Field idField = null;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.fields = ReflectionHelper.getFields(clazz);

        for (Field field : this.fields) {
            if (field.isAnnotationPresent(Id.class)) {
                this.idField = field;
            } else {
                this.fieldsWithoutId.add(field);
            }
        }
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new JdbcMapperImplException("Не найден конструктор по умолчанию.");
        }
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(fields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
