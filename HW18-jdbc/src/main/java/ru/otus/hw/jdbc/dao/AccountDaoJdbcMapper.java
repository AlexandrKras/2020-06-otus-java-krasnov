package ru.otus.hw.jdbc.dao;

import ru.otus.hw.core.dao.AccountDao;
import ru.otus.hw.core.model.Account;
import ru.otus.hw.core.sessionmanager.SessionManager;
import ru.otus.hw.jdbc.DbExecutorImpl;
import ru.otus.hw.jdbc.mapper.JdbcMapper;
import ru.otus.hw.jdbc.sessionmanager.SessionManagerJdbc;

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
