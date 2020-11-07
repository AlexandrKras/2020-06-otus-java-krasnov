package ru.otus.hw.hibernate.dao;

import ru.otus.hw.model.User;
import ru.otus.hw.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();

    Optional<User> findByLogin(String login);

    List<User> getAllUsers();
}
