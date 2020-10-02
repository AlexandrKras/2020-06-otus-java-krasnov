package ru.otus.hw.core.dao;

import java.util.Optional;

import ru.otus.hw.core.model.User;
import ru.otus.hw.core.sessionmanager.SessionManager;

public interface UserDao {
    Optional<User> findById(Object id);

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
