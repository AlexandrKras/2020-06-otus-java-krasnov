package ru.otus.jdbc.mapper;

import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

/**
 * Сохратяет объект в базу, читает объект из базы
 * @param <T>
 */
public interface JdbcMapper<T> {
    void insert(T objectData);

    void update(T objectData);

    void insertOrUpdate(T objectData);

    T findById(Object id, Class<T> clazz);

    void init(DbExecutor<T> dbExecutor, SessionManagerJdbc sessionManagerJdbc);
}
