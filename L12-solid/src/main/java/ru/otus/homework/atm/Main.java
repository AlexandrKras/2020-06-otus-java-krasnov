package ru.otus.homework.atm;

import ru.otus.homework.atm.deposit.Banknote.IBanknote;
import ru.otus.homework.atm.deposit.CashMachine;

public class Main {
    public static void main(String[] args) {
        CashMachine cashMachine = new CashMachine();

        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.getMoney(14900));
        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.getMoney(14999));
        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.putMoney(IBanknote.B2000));
        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.putMoney(new IBanknote() {
            @Override
            public int getNominal() {
                return 10000;
            }
        }));
        System.out.println(cashMachine.getRemnantMoney());
        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.getMoney(500));
        System.out.println("Баланс: " + cashMachine.getBalace());
    }
}
