package ru.otus.homework.atm.deposit;

import ru.otus.homework.atm.deposit.Banknote.IBanknote;

public class DepositBox5000 extends DepositBox {
    @Override
    protected IBanknote createBanknote() {
        return IBanknote.B5000;
    }
}
