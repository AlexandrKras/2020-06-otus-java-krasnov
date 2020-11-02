package ru.otus.hw;

import org.hibernate.SessionFactory;
import ru.otus.hw.core.dao.UserDao;
import ru.otus.hw.core.model.AddressDataSet;
import ru.otus.hw.core.model.PhoneDataSet;
import ru.otus.hw.core.model.User;
import ru.otus.hw.core.server.UsersWebServer;
import ru.otus.hw.core.server.UsersWebServerWithFilterBasedSecurity;
import ru.otus.hw.core.service.*;
import ru.otus.hw.flyway.MigrationsExecutor;
import ru.otus.hw.flyway.MigrationsExecutorFlyway;
import ru.otus.hw.hibernate.HibernateUtils;
import ru.otus.hw.hibernate.dao.UserDaoHibernate;
import ru.otus.hw.hibernate.sessionmanager.SessionManagerHibernate;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithFilterBasedSecurityDemo {
    public static final String HIBERNATE_CFG_FILE = "/hibernate/hibernate.cfg.xml";

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/web/templates/";

    public static void main(String[] args) throws Exception {
        MigrationsExecutor migrationsExecutor = new MigrationsExecutorFlyway(HIBERNATE_CFG_FILE);
        migrationsExecutor.executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE
                , User.class
                , AddressDataSet.class
                , PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceUser, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}