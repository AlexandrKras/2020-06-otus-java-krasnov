package ru.otus.hw.hibernate.dao;

public class UserDaoException extends RuntimeException {
    public UserDaoException(Exception ex) {
        super(ex);
    }
}
