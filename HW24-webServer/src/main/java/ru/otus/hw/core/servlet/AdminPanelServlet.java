package ru.otus.hw.core.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.core.model.AddressDataSet;
import ru.otus.hw.core.model.PhoneDataSet;
import ru.otus.hw.core.model.User;
import ru.otus.hw.core.service.DBServiceUser;
import ru.otus.hw.core.service.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class AdminPanelServlet extends HttpServlet {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_ADDRESS = "address";
    private static final String PARAM_PHONES = "phones";
    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_USERS = "users";

    private final Logger logger = LoggerFactory.getLogger(AdminPanelServlet.class);
    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public AdminPanelServlet(DBServiceUser dbServiceUser, TemplateProcessor templateProcessor) {
        this.dbServiceUser = dbServiceUser;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<User> users = dbServiceUser.getAllUsers();
        paramsMap.put(TEMPLATE_ATTR_USERS, users);

        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = createUser(req);
        if (user.getName().isEmpty()) {
            resp.setStatus(SC_BAD_REQUEST);
        } else {
            try {
                dbServiceUser.saveUser(user);
                resp.sendRedirect("/users");
            } catch (Exception e) {
                logger.error("User save error", e);
                resp.setStatus(SC_BAD_REQUEST);
            }
        }
    }

    private User createUser(HttpServletRequest req) {
        String name = req.getParameter(PARAM_NAME);
        String address = req.getParameter(PARAM_ADDRESS);
        String phones = req.getParameter(PARAM_PHONES);
        User user = new User(name);

        if (!address.isEmpty()) {
            user.setAddressDataSet(new AddressDataSet(address));
        }

        if (!phones.isEmpty()) {
            String[] listPhones = phones.split(",");
            Arrays.stream(listPhones).forEach(phone -> {
                user.addPhoneDataSet(new PhoneDataSet(phone, user));
            });
        }
        return user;
    }
}