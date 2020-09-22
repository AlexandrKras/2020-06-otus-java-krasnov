package ru.otus.homework;

import com.google.gson.Gson;
import ru.otus.homework.model.Address;
import ru.otus.homework.model.Person;

public class Main {
    public static void main(String[] args) {
        MyGson myGson = new MyGson();
        Person person = createPerson();
        String json = myGson.toJson(person);
        System.out.println(json);

        Gson gson = new Gson();
        Person gsonPerson = gson.fromJson(json, Person.class);
        System.out.println(person.equals(gsonPerson));
    }

    private static Person createPerson() {
        Person person = new Person("Иванов", "Иван", "Иванович", 30);
        person.addPhone("8 (800 88 88 888)");
        person.addPhone("8 (800 44 44 444)");
        Address residentialAddress = new Address("Россия", "Москва", "Ленина", 41, 10);
        Address postalAddress = new Address("Россия", "Москва", "Пушкина", 31, 11);
        person.addAdress("Проживания", residentialAddress);
        person.addAdress("Почтовый", postalAddress);
        return person;
    }
}
