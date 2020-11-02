package ru.otus.hw.flyway;

public interface MigrationsExecutor {
    void cleanDb();
    void executeMigrations();
}