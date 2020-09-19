package ru.otus.homework.model;

import java.util.Objects;

public class Address {
    private final String country;
    private final String city;
    private final String street;
    private final int home;
    private final int apartment;

    public Address(String country, String city, String street, int home, int apartment) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.home = home;
        this.apartment = apartment;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHome() {
        return home;
    }

    public int getApartment() {
        return apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return home == address.home &&
                apartment == address.apartment &&
                country.equals(address.country) &&
                city.equals(address.city) &&
                street.equals(address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, home, apartment);
    }
}
