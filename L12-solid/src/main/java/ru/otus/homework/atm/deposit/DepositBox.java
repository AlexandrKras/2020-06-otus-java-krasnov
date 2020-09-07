package ru.otus.homework.atm.deposit;


import ru.otus.homework.atm.deposit.Banknote.Banknote;

import java.util.HashMap;
import java.util.Map;

public abstract class DepositBox {
    private DepositBox next;
    private int count;

    public Map<Banknote, Integer> getBanknotes(int sum) {
        Map<Banknote, Integer> banknotes = new HashMap<>();
        int remnant = sum;
        int countBanknotes = checkBanknotesInBox(sum);
        banknotes.put(createBanknote(), countBanknotes);
        count -= countBanknotes;
        remnant -= getNominal() * countBanknotes;

        if (remnant > 0 && getNext() != null) {
            banknotes.putAll(getNext().getBanknotes(remnant));
        }

        return banknotes;
    }

    public Banknote putBanknotes(Banknote banknote) {
        if (banknote.getNominal() == getNominal()) {
            count++;
        } else {
            if (getNext() != null) {
                return getNext().putBanknotes(banknote);
            } else {
                return banknote;
            }
        }
        return null;
    }

    private int checkBanknotesInBox(int cash) {
        int requiredNumberOfBills = cash / getNominal();
        if (requiredNumberOfBills <= count)
            return requiredNumberOfBills;
        else if (requiredNumberOfBills > count)
            return count;
        return 0;
    }

    public int getBalance() {
        int balance = count * getNominal();
        if (getNext() != null)
            balance += getNext().getBalance();
        return balance;
    }

    private int getNominal() {
        return createBanknote().getNominal();
    }

    protected abstract Banknote createBanknote();

    public DepositBox getNext() {
        return next;
    }

    public void setNext(DepositBox next) {
        this.next = next;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
