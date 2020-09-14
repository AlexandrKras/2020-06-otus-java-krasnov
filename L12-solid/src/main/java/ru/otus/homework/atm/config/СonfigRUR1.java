package ru.otus.homework.atm.config;

import java.util.HashMap;
import java.util.Map;

public class СonfigRUR1 implements Сonfiguration{
    private enum Banknote {
        B5000(5000),
        B1000(1000),
        B500(500),
        B100(100);

        private int nominal;

        Banknote(int nominal) {
            this.nominal = nominal;
        }

        public int getNominal() {
            return nominal;
        }
    }

    @Override
    public Map<Integer, Integer> createСonfig(int count) {
        Map<Integer, Integer> deposit = new HashMap<>();
        for (var banknote : Banknote.values()) {
            deposit.put(banknote.getNominal(), count);
        }
        return deposit;
    }
}
