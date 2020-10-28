package ru.otus.hw.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AddressDataSet addressDataSet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PhoneDataSet> phones;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(long id, String login, String name) {
        this.id = id;
        this.login = login;
        this.password = login;
        this.name = name;
    }

    public User(long id, String login, String password, String name) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
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

    public AddressDataSet getAddressDataSet() {
        return addressDataSet;
    }

    public void setAddressDataSet(AddressDataSet addressDataSet) {
        this.addressDataSet = addressDataSet;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public void addPhoneDataSet(PhoneDataSet phone) {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        phones.add(phone);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", addressDataSet=" + addressDataSet +
                ", phones=" + phones +
                '}';
    }
}