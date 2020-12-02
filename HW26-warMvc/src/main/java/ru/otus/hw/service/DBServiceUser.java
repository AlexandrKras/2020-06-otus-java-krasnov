package ru.otus.hw.service;

import ru.otus.hw.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> findByLogin(String login);

    List<User> getAllUsers();
}