package ru.otus.hw.jdbc.mapper.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.core.model.User;
import ru.otus.hw.jdbc.DbExecutor;
import ru.otus.hw.jdbc.exception.JdbcMapperException;
import ru.otus.hw.jdbc.mapper.JdbcMapper;
import ru.otus.hw.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);

    private final EntitySQLMetaDataImpl metaData;
    private DbExecutor<T> dbExecutor;
    private SessionManagerJdbc sessionManagerJdbc;

    public JdbcMapperImpl(EntitySQLMetaDataImpl metaData) {
        this.metaData = metaData;
    }

    private static void printLogInfo(String sql, Object... obj) {
        logger.info("SQL: {}", sql);
        logger.info("Values: {}", obj);
    }

    public void init(DbExecutor<T> dbExecutor, SessionManagerJdbc sessionManagerJdbc) {
        this.dbExecutor = dbExecutor;
        this.sessionManagerJdbc = sessionManagerJdbc;
    }

    @Override
    public void insert(T objectData) {
        String sql = metaData.getInsertSql();
        List list = metaData.getFieldsValue(objectData, metaData.getData().getFieldsWithoutId());
        printLogInfo(sql, list);

        try {
            long id = getDbExecutor().executeInsert(getConnection()
                    , sql
                    , list);
            metaData.setFieldsIdValue(objectData, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JdbcMapperException("При добавлении объекта произошла ошибка.");
        }
    }

    @Override
    public void update(T objectData) {
        String sql = metaData.getUpdateSql();
        List list = metaData.getFieldsValue(objectData, metaData.getData().getFieldsWithoutId());
        list.add(metaData.getFieldIdValue(objectData));
        printLogInfo(sql, list);

        try {
            getDbExecutor().executeInsert(getConnection()
                    , sql
                    , list);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JdbcMapperException("При обновлении объекта произошла ошибка.");
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        Object id = metaData.getFieldIdValue(objectData);
        if (id == null) {
            insert(objectData);
        } else {
            update(objectData);
        }
    }

    @Override
    public T findById(Object id, Class clazz) {
        Optional<User> user = Optional.empty();
        String sql = metaData.getSelectByIdSql();
        printLogInfo(sql, id);
        try {
            user = (Optional<User>) getDbExecutor().executeSelect(getConnection()
                    , sql
                    , id
                    , rs -> {
                        try {
                            if (rs.next()) {
                                return createObject(rs);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new JdbcMapperException("Ошибка доступа к столбцу, Или ResultSetset недоступен.");
                        }
                        return null;
                    });
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JdbcMapperException("Поиск по ID не удался.");
        }
        return (T) user.get();
    }

    private T createObject(ResultSet resultSet) {
        try {
            final Object instance = metaData.getData().getConstructor().newInstance();
            return (T) metaData.setFieldsValue(instance, resultSet);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new JdbcMapperException("Ошибка при создании экземпляра класса");
        }
    }

    private Connection getConnection() {
        if (dbExecutor == null)
            throw new JdbcMapperException("JdbcMapper не проинициализирован. Выполните инициализацию.");
        return sessionManagerJdbc.getCurrentSession().getConnection();
    }

    private DbExecutor<T> getDbExecutor() {
        if (dbExecutor == null)
            throw new JdbcMapperException("JdbcMapper не проинициализирован. Выполните инициализацию.");
        return dbExecutor;
    }
}
