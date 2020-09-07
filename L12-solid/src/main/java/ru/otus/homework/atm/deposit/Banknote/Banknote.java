package ru.otus.homework.atm.deposit.Banknote;

public enum Banknote {
    B2000 (2000),
    B5000 (5000),
    B1000 (1000),
    B500 (500),
    B200 (200),
    B100 (100);

    private int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
