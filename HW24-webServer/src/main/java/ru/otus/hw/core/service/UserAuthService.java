package ru.otus.hw.core.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}