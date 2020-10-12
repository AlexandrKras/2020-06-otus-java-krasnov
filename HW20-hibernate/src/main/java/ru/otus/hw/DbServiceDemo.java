package ru.otus.hw;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.core.dao.UserDao;
import ru.otus.hw.core.model.AddressDataSet;
import ru.otus.hw.core.model.PhoneDataSet;
import ru.otus.hw.core.model.User;
import ru.otus.hw.core.service.DBServiceUser;
import ru.otus.hw.core.service.DbServiceUserImpl;
import ru.otus.hw.flyway.MigrationsExecutor;
import ru.otus.hw.flyway.MigrationsExecutorFlyway;
import ru.otus.hw.hibernate.HibernateUtils;
import ru.otus.hw.hibernate.dao.UserDaoHibernate;
import ru.otus.hw.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class DbServiceDemo {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        MigrationsExecutor migrationsExecutor = new MigrationsExecutorFlyway(HIBERNATE_CFG_FILE);
        migrationsExecutor.executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE
                , User.class
                , AddressDataSet.class
                , PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user = new User(0, "Иванов Иван Иванович");
        user.setAddressDataSet(new AddressDataSet("Пермь, Ленина д.95 кв.10"));
        user.addPhoneDataSet(new PhoneDataSet("8 (800) 456 45 56", user));
        user.addPhoneDataSet(new PhoneDataSet("8 (800) 123 12 23", user));
        long id = dbServiceUser.saveUser(user);
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        outputUserOptional("Created user", mayBeCreatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }
}
