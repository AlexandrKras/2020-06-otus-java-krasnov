package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    Object saveUser(User user);

    Optional<User> getUser(Object id);
}
