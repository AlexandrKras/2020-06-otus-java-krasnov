package ru.otus.jdbc.dao;

import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoJdbcMapper implements AccountDao {
    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<Account> jdbcMapper;

    public AccountDaoJdbcMapper(SessionManagerJdbc sessionManager, DbExecutorImpl<Account> dbExecutor, JdbcMapper<Account> jdbcMapper) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = jdbcMapper;
        jdbcMapper.init(dbExecutor, sessionManager);
    }

    @Override
    public Optional<Account> findById(Object id) {
        return Optional.of(jdbcMapper.findById(id, Account.class));
    }

    @Override
    public long insertUser(Account account) {
        jdbcMapper.insert(account);
        return 0;
    }

    @Override
    public void updateUser(Account account) {
        jdbcMapper.update(account);
    }

    @Override
    public void insertOrUpdate(Account account) {
        jdbcMapper.insertOrUpdate(account);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
