package ru.otus.hw.core.dao;

import ru.otus.hw.core.model.Account;
import ru.otus.hw.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findById(Object id);

    long insertUser(Account account);

    void updateUser(Account account);

    void insertOrUpdate(Account account);

    SessionManager getSessionManager();
}
