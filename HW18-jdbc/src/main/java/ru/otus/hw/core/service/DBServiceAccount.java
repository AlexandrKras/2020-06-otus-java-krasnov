package ru.otus.hw.core.service;

import ru.otus.hw.core.model.Account;
import ru.otus.hw.core.model.User;

import java.util.Optional;

public interface DBServiceAccount {

    Object saveAccount(Account account);

    Optional<Account> getAccount(Object id);
}
