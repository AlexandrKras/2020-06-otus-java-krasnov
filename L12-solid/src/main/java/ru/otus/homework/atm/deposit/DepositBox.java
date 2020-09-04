package ru.otus.homework.atm.deposit;

import ru.otus.homework.atm.deposit.Banknote.IBanknote;
import ru.otus.homework.atm.deposit.Banknote.StackBanknotes;

public abstract class DepositBox {
    private DepositBox next;
    private int count;

    public StackBanknotes getBanknotes(int sum) {
        StackBanknotes banknotes = new StackBanknotes();
        int remnant = sum;
        int counttBanknotes = checkBanknotesInBox(sum);
        for (int n = 0; n < counttBanknotes; n++) {
            banknotes.addBanknote(createBanknote());
            --count;
            remnant -= getNominal();
        }

        if (remnant > 0 && getNext() != null) {
            banknotes.addAllBanknotes(getNext().getBanknotes(remnant).getStackBanknotes());
        }

        return banknotes;
    }

    public IBanknote putBanknotes(IBanknote banknote) {
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

    protected abstract IBanknote createBanknote();

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
