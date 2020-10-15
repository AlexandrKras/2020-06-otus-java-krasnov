package ru.otus.hw;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.cachehw.HwListener;
import ru.otus.hw.cachehw.MyCache;
import ru.otus.hw.core.dao.UserDao;
import ru.otus.hw.core.model.User;
import ru.otus.hw.core.service.implementation.DbServiceMyCacheUserImpl;
import ru.otus.hw.core.service.implementation.DbServiceUserImpl;
import ru.otus.hw.h2.DataSourceH2;
import ru.otus.hw.jdbc.DbExecutorImpl;
import ru.otus.hw.jdbc.dao.UserDaoJdbcMapper;
import ru.otus.hw.jdbc.mapper.JdbcMapper;
import ru.otus.hw.jdbc.mapper.implementation.EntityClassMetaDataImpl;
import ru.otus.hw.jdbc.mapper.implementation.EntitySQLMetaDataImpl;
import ru.otus.hw.jdbc.mapper.implementation.JdbcMapperImpl;
import ru.otus.hw.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.util.Optional;


public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        var dataSource = new DataSourceH2();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);

        DbExecutorImpl<User> dbExecutorUser = new DbExecutorImpl<>();
        JdbcMapper<User> jdbcMapperUser = new JdbcMapperImpl<>(new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<User>(User.class)));
        UserDao userDao = new UserDaoJdbcMapper(sessionManager, dbExecutorUser, jdbcMapperUser);

        var dbServiceUser = new DbServiceUserImpl(userDao);
        var hwListener = new HwListener<Object, User>() {
            @Override
            public void notify(Object key, User value, String action) {
                logger.info("ACTION key:{}, value:{}, action: {}", key, value, action);
            }
        };
        MyCache cache = createCache(hwListener);
        DbServiceMyCacheUserImpl dbServiceMyCacheUser = new DbServiceMyCacheUserImpl(dbServiceUser, cache);
        dbServiceMyCacheUser.saveUser(new User(null, "dbServiceUser", 30));
        var id = dbServiceMyCacheUser.saveUser(new User(null, "dbServiceUser2", 40));
        dbServiceMyCacheUser.saveUser(new User(null, "dbServiceUser3", 50));

        Optional<User> user = dbServiceMyCacheUser.getUser(id);
        user.get().setAge(45);
        dbServiceMyCacheUser.saveUser(user.get());

        cache.removeListener(hwListener);
    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }

    private static MyCache<Object, User> createCache(HwListener hwListener) {
        MyCache<Object, User> myCache = new MyCache();
        myCache.addAction(MyCache.Actions.CREATE);
        myCache.addListener(hwListener);

        return myCache;
    }
}
