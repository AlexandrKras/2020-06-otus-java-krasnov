package ru.otus.hw.core.service;

import ru.otus.hw.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> findByLogin(String login);

    List<User> getAllUsers();
}