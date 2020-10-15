package ru.otus.hw.jdbc.dao;

import ru.otus.hw.core.dao.UserDao;
import ru.otus.hw.core.model.User;
import ru.otus.hw.core.sessionmanager.SessionManager;
import ru.otus.hw.jdbc.DbExecutorImpl;
import ru.otus.hw.jdbc.mapper.JdbcMapper;
import ru.otus.hw.jdbc.sessionmanager.SessionManagerJdbc;

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
