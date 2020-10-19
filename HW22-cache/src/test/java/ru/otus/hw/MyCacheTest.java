package ru.otus.hw;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
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

import java.util.HashSet;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(MILLISECONDS)
public class MyCacheTest {
    private static final Logger logger = LoggerFactory.getLogger(MyCacheTest.class);

    private static DbServiceUserImpl dbServiceUser;
    private static DbServiceMyCacheUserImpl dbServiceMyCacheUser;
    private static MyCache<Object, User> cache;
    private static HwListener<Object, User> listener;
    private static Set<Object> setIdUsers = new HashSet<>();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(MyCacheTest.class.getSimpleName()).forks(1).build();
        new Runner(opt).run();
    }

    @TearDown
    public static void TearDown() {
        cache.removeListener(listener);
    }

    @Setup
    public static void setup() throws Exception {
        var dataSource = new DataSourceH2();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);

        DbExecutorImpl<User> dbExecutorUser = new DbExecutorImpl<>();
        JdbcMapper<User> jdbcMapperUser = new JdbcMapperImpl<>(new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<User>(User.class)));
        UserDao userDao = new UserDaoJdbcMapper(sessionManager, dbExecutorUser, jdbcMapperUser);

        dbServiceUser = new DbServiceUserImpl(userDao);
        listener = new HwListener<Object, User>() {
            @Override
            public void notify(Object key, User value, String action) {
                logger.info("ACTION key:{}, value:{}, action: {}", key, value, action);
            }
        };
        cache = createCache(listener);
        dbServiceMyCacheUser = new DbServiceMyCacheUserImpl(dbServiceUser, cache);
        for (int n = 0; n < 100; n++) {
            setIdUsers.add(dbServiceMyCacheUser.saveUser(getUser(n)));
        }
    }

    private static void flywayMigrations(DataSource dataSource) {
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
    }

    @Benchmark
    public void tsingTheCacheTest() throws Exception {
        for (Object object : setIdUsers) {
            dbServiceMyCacheUser.getUser(object);
        }
    }

    @Benchmark
    public void withoutUsingTheCacheTest() throws Exception {
        for (Object object : setIdUsers) {
            dbServiceUser.getUser(object);
        }
    }

    private static User getUser(int numberUser) {
        return new User(null, String.format("user%s", numberUser), (int) (Math.random() * 10));
    }

    private static MyCache<Object, User> createCache(HwListener hwListener) {
        MyCache<Object, User> myCache = new MyCache();
        myCache.addAction(MyCache.Actions.CREATE);
        myCache.addListener(hwListener);

        return myCache;
    }
}
