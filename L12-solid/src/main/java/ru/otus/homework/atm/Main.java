package ru.otus.homework.atm;

import ru.otus.homework.atm.config.ConfigurationFactory;

public class Main {


    public static void main(String[] args) {
        ATM atm = new ATM(ConfigurationFactory.getConfiguration(ConfigurationFactory.RUR12102017));

        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.getMoney(14900));
        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.getMoney(14999));
        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.putMoney(2000));
        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.getRemnantMoney());
        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.getMoney(500));
        System.out.println("Баланс: " + atm.getBalace());

        atm = new ATM(ConfigurationFactory.getConfiguration(ConfigurationFactory.RUR01011999));

        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.getMoney(14900));
        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.getMoney(14999));
        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.putMoney(2000));
        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.getRemnantMoney());
        System.out.println("Баланс: " + atm.getBalace());
        System.out.println(atm.getMoney(500));
        System.out.println("Баланс: " + atm.getBalace());
    }
}
