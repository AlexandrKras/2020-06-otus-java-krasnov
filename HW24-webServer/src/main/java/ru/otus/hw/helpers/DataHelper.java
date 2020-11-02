package ru.otus.hw.helpers;

import ru.otus.hw.core.model.AddressDataSet;
import ru.otus.hw.core.model.PhoneDataSet;
import ru.otus.hw.core.model.User;
import ru.otus.hw.core.service.DBServiceUser;

public class DataHelper {

    public static void fillDb(DBServiceUser dbServiceUser) {
        createUsers(dbServiceUser);
        createAdmin(dbServiceUser);
    }

    public static void createUsers(DBServiceUser dbServiceUser) {
        for (int n = 1; n <= 10; n++) {
            User user = new User(0
                    , String.format("login%s", n)
                    , String.format("user%s", n));
            user.setAddressDataSet(new AddressDataSet(String.format("Address%s", n)));
            user.addPhoneDataSet(new PhoneDataSet(String.format("phone%s", n), user));

            dbServiceUser.saveUser(user);
        }
    }

    public static void createAdmin(DBServiceUser dbServiceUser) {
        User user = new User(0, "Admin", "Admin", "Алекcандр");
        user.setAddressDataSet(new AddressDataSet("Adress"));
        dbServiceUser.saveUser(user);
    }
}