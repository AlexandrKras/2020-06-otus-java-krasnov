package ru.otus.hw.core.service.implementation;

import ru.otus.hw.cachehw.MyCache;
import ru.otus.hw.core.model.User;
import ru.otus.hw.core.service.DBServiceUser;

import java.util.Optional;

public class DbServiceMyCacheUserImpl implements DBServiceUser {
    private final DBServiceUser dbServiceUser;
    private final MyCache<Object, User> cache;

    public DbServiceMyCacheUserImpl(DBServiceUser dbServiceUser, MyCache<Object, User> cache) {
        this.dbServiceUser = dbServiceUser;
        this.cache = cache;
    }

    @Override
    public Object saveUser(User user) {
        Object id = dbServiceUser.saveUser(user);
        cache.put(id, user.copy());

        return id;
    }

    @Override
    public Optional<User> getUser(Object id) {
        User userFromCache = cache.get(id);
        if (userFromCache == null) {
            var userFromDb = dbServiceUser.getUser(id);
            userFromDb.ifPresent(u -> cache.put(id, u.copy()));
            return userFromDb;
        }
        return Optional.of(userFromCache.copy());
    }
}
