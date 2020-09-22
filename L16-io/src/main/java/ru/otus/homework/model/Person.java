package ru.otus.homework.model;

import java.util.*;

public class Person {
    private String surname;
    private String name;
    private String patronymic;
    private int age;
    private List<String> phone = new ArrayList<>();
    private Map<String, Address> listAdress = new HashMap<>();

    public Person(String surname, String name, String patronymic, int age) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getAge() {
        return age;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public void addPhone(String phone) {
        this.phone.add(phone);
    }

    public Map<String, Address> getListAdress() {
        return listAdress;
    }

    public void setListAdress(Map<String, Address> listAdress) {
        this.listAdress = listAdress;
    }

    public void addAdress(String str, Address address) {
        this.listAdress.put(str, address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age &&
                surname.equals(person.surname) &&
                name.equals(person.name) &&
                patronymic.equals(person.patronymic) &&
                Objects.equals(phone, person.phone) &&
                Objects.equals(listAdress, person.listAdress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic, age, phone, listAdress);
    }
}
