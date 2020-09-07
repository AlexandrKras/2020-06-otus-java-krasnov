package ru.otus.homework.atm;

import ru.otus.homework.atm.deposit.Banknote.Banknote;
import ru.otus.homework.atm.deposit.CashMachine;
import ru.otus.homework.atm.deposit.DepositBox;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        DepositBox box = createСonfiguration(10);
        CashMachine cashMachine = new CashMachine(box);

        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.getMoney(14900));
        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.getMoney(14999));
        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.putMoney(Banknote.B2000));
        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.getRemnantMoney());
        System.out.println("Баланс: " + cashMachine.getBalace());
        System.out.println(cashMachine.getMoney(500));
        System.out.println("Баланс: " + cashMachine.getBalace());
    }

    private static DepositBox createСonfiguration(int initCount) {
        Map<Banknote, Integer> map = new TreeMap<>(Comparator.comparing(Banknote::getNominal).reversed());
        for (Banknote banknote2 : Banknote.values()) {
            map.put(banknote2, initCount);
        }

        DepositBox boxRoot = null, box  = null;
        for (var entry : map.entrySet()) {
            DepositBox innerBox = new DepositBox() {
                @Override
                protected Banknote createBanknote() {
                    return entry.getKey();
                }
            };
            innerBox.setCount(entry.getValue());

            if (box == null) {
                box = innerBox;
                boxRoot = box;
            } else {
                box.setNext(innerBox);
                box = innerBox;
            }
        }
        return boxRoot;
    }
}
