package ru.otus.hw.core.service;

import ru.otus.hw.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

}
