package ru.otus.homework.atm.deposit;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CashMachine {
    private Map<Integer, Integer> storage = new TreeMap<>(Comparator.reverseOrder());

    public CashMachine(Map<Integer, Integer> storage) {
        this.storage.putAll(storage);
    }

    public int getBalace() {
        int result = 0;
        for (var box : storage.entrySet()) {
            result += box.getKey() * box.getValue();
        }
        return result;
    }

    public Map<Integer, Integer> getCash(int sum) {
        Map<Integer, Integer> returnBanknotes = new HashMap<>();
        int remnant = sum;
        for (var entry : storage.entrySet()) {
            if (entry.getValue() == 0) {
                continue;
            }

            int countBanknotes = checkBanknotesInBox(remnant, entry);
            returnBanknotes.put(entry.getKey(), countBanknotes);
            remnant -= entry.getKey() * countBanknotes;

            if (remnant == 0) {
                break;
            }
        }

        if (remnant == 0) {
            for (var banknotes : returnBanknotes.entrySet()) {
                storage.put(banknotes.getKey(), storage.get(banknotes.getKey()) - banknotes.getValue());
            }
            return returnBanknotes;
        }

        return null;
    }

    public Integer putBanknotes(int banknote) {
        if (storage.get(banknote) != null) {
            storage.put(banknote, storage.get(banknote) + 1);
        } else {
            return banknote;
        }
        return null;
    }

    private int checkBanknotesInBox(int cash, Map.Entry<Integer, Integer> deposit) {
        int requiredNumberOfBills = cash / deposit.getKey();
        if (requiredNumberOfBills <= deposit.getValue())
            return requiredNumberOfBills;
        else if (requiredNumberOfBills > deposit.getValue())
            return deposit.getValue();
        return 0;
    }
}
