package ru.otus.jdbc.dao;

import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class UserDaoJdbcMapper implements UserDao {
    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<User> jdbcMapper;

    public UserDaoJdbcMapper(SessionManagerJdbc sessionManager, DbExecutorImpl<User> dbExecutor, JdbcMapper<User> jdbcMapper) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = jdbcMapper;
        jdbcMapper.init(dbExecutor, sessionManager);
    }

    @Override
    public Optional<User> findById(Object id) {
        return Optional.of(jdbcMapper.findById(id, User.class));
    }

    @Override
    public long insertUser(User user) {
        jdbcMapper.insert(user);
        return 0;
    }

    @Override
    public void updateUser(User user) {
        jdbcMapper.update(user);
    }

    @Override
    public void insertOrUpdate(User user) {
        jdbcMapper.insertOrUpdate(user);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
