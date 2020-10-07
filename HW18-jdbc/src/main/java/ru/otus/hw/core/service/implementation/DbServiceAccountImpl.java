package ru.otus.hw.core.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.core.dao.AccountDao;
import ru.otus.hw.core.model.Account;
import ru.otus.hw.core.service.DBServiceAccount;
import ru.otus.hw.core.service.exception.DbServiceException;

import java.util.Optional;

public class DbServiceAccountImpl implements DBServiceAccount {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

    private final AccountDao accountDao;

    public DbServiceAccountImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Override
    public Object saveAccount(Account account) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                accountDao.insertOrUpdate(account);
                sessionManager.commitSession();

                logger.info("created account: {}", account.getNo());
                return account.getNo();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Account> getAccount(Object id) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> accountOptional = accountDao.findById(id);

                logger.info("user: {}", accountOptional.orElse(null));
                return accountOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
