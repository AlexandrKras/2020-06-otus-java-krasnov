package ru.otus.hw.core.model;

import ru.otus.hw.annotations.Id;

public class Account {
    @Id
    private Object no;
    private String type;
    private Integer rest;

    public Account() {
        super();
    }

    public Account(Object no, String type, Integer rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public Object getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRest() {
        return rest;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
