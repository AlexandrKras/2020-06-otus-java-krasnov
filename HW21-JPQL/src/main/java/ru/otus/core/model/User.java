package ru.otus.core.model;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class User {
    private Object id;
    private String name;
    private Integer age;

    public User() {
        super();
    }

    public User(Object id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
