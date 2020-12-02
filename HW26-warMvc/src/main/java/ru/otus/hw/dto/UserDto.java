package ru.otus.hw.dto;

import ru.otus.hw.model.AddressDataSet;
import ru.otus.hw.model.PhoneDataSet;
import ru.otus.hw.model.User;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDto {
    private long id;
    private String name = "";
    private String street = "";
    private String phone = "";

    public UserDto() {
    }

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        street = Optional.of(user.getAddressDataSet().getStreet()).orElse("");
        phone = user.getPhones().stream()
                .map(PhoneDataSet::getNumber)
                .collect(Collectors.joining(", "));

        phone = Optional.of(phone).orElse(",").substring(1);
    }

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        if (street != null) {
            user.setAddressDataSet(new AddressDataSet(street));
        }
        if (phone != null) {
            Arrays.stream(phone.split(",")).forEach(n -> user.addPhoneDataSet(new PhoneDataSet(n, user)));
        }

        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
