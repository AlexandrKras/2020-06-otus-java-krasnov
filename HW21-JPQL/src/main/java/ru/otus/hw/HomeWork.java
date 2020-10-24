package ru.otus.hw;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.implementation.DbServiceUserImpl;
import ru.otus.hw.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.UserDaoJdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.util.Optional;


public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DataSourceH2();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);

// Работа с пользователем
        DbExecutorImpl<User> dbExecutorUser = new DbExecutorImpl<>();
//        JdbcMapper<User> jdbcMapperUser = null;// new JdbcMapperImpl<>(new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<User>(User.class)));
        UserDao userDao = new UserDaoJdbcMapper(sessionManager, dbExecutorUser/*, jdbcMapperUser*/);

// Код дальше должен остаться, т.е. userDao должен использоваться
        var dbServiceUser = new DbServiceUserImpl(userDao);
        dbServiceUser.saveUser(new User(null, "dbServiceUser", 30));
        var id = dbServiceUser.saveUser(new User(null, "dbServiceUser2", 40));
        dbServiceUser.saveUser(new User(null, "dbServiceUser3", 50));
        Optional<User> user = dbServiceUser.getUser(id);
        user.get().setAge(45);
        dbServiceUser.saveUser(user.get());

        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );
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
}
