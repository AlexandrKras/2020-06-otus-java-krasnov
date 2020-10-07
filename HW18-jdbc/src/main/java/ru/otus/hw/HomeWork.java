package ru.otus.hw;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.core.dao.AccountDao;
import ru.otus.hw.core.dao.UserDao;
import ru.otus.hw.core.model.Account;
import ru.otus.hw.core.model.User;
import ru.otus.hw.core.service.implementation.DbServiceAccountImpl;
import ru.otus.hw.core.service.implementation.DbServiceUserImpl;
import ru.otus.hw.h2.DataSourceH2;
import ru.otus.hw.jdbc.DbExecutorImpl;
import ru.otus.hw.jdbc.dao.AccountDaoJdbcMapper;
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
// Общая часть
        var dataSource = new DataSourceH2();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);

// Работа с пользователем
        DbExecutorImpl<User> dbExecutorUser = new DbExecutorImpl<>();
        JdbcMapper<User> jdbcMapperUser = new JdbcMapperImpl<>(new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<User>(User.class)));
        UserDao userDao = new UserDaoJdbcMapper(sessionManager, dbExecutorUser, jdbcMapperUser);

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

// Работа со счетом
        DbExecutorImpl<Account> dbExecutorAccount = new DbExecutorImpl<>();
        JdbcMapper<Account> jdbcMapperAccount = new JdbcMapperImpl<>(new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<Account>(Account.class)));
        AccountDao accountDao = new AccountDaoJdbcMapper(sessionManager, dbExecutorAccount, jdbcMapperAccount);

        var dbServiceAccount = new DbServiceAccountImpl(accountDao);
        dbServiceAccount.saveAccount(new Account(null, "dbServiceAccount", 10));
        id = dbServiceAccount.saveAccount(new Account(null, "dbServiceAccount2", 20));
        dbServiceAccount.saveAccount(new Account(null, "dbServiceAccount3", 30));
        Optional<Account> account = dbServiceAccount.getAccount(id);
        account.get().setRest(100);
        dbServiceAccount.saveAccount(account.get());
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
