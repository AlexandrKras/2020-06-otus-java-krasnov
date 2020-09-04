package ru.otus.homework.atm.deposit;

import ru.otus.homework.atm.deposit.Banknote.IBanknote;

public class DepositBox1000 extends DepositBox {

    @Override
    protected IBanknote createBanknote() {
        return IBanknote.B1000;
    }
}
