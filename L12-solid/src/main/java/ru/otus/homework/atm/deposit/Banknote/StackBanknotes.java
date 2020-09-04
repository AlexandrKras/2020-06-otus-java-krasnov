package ru.otus.homework.atm.deposit.Banknote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StackBanknotes {
    List<IBanknote> stackBanknotes = new ArrayList<>();

    public void addBanknote(IBanknote banknote) {
        stackBanknotes.add(banknote);
    }

    public void addAllBanknotes(List<IBanknote> banknotes) {
        stackBanknotes.addAll(banknotes);
    }

    public List<IBanknote> getStackBanknotes() {
        return Collections.unmodifiableList(stackBanknotes);
    }

    public long getSum() {
        long sum = 0;
        for (IBanknote banknote : stackBanknotes) {
            sum += banknote.getNominal();
        }
        return sum;
    }
}
