package ru.otus.homework.atm.deposit;

import ru.otus.homework.atm.deposit.Banknote.Banknote;
import ru.otus.homework.atm.deposit.DepositBox;

import java.util.Map;

public class CashMachine {
    private DepositBox deposit;

    public CashMachine(DepositBox deposit) {
        this.deposit = deposit;
    }

    public String getMoney(int sum) {
        String mess = validateSum(sum);
        Map<Banknote, Integer> stackBanknotes = null;
        if (mess.isEmpty()) {
            stackBanknotes = deposit.getBanknotes(sum);
        } else {
            return mess;
        }

        if (stackBanknotes.isEmpty()) {
            return "Не возможно выдать запрошеную вами сумму";
        }

        return "Сумма " + calculateTheAmount(stackBanknotes) + " выдана";
    }

    private int calculateTheAmount(Map<Banknote, Integer> stackBanknotes) {
        int sum = 0;
        for (Map.Entry<Banknote, Integer> entry : stackBanknotes.entrySet()) {
            sum += entry.getKey().getNominal() * entry.getValue();
        }
        return sum;
    }

    public String getRemnantMoney() {
        Map<Banknote, Integer> stackBanknotes = deposit.getBanknotes(getBalace());

        return "Выдан остаток денежных средств в размере " + calculateTheAmount(stackBanknotes);
    }

    public String putMoney(Banknote newBanknote) {
        Banknote banknote2 = deposit.putBanknotes(newBanknote);
        if (banknote2 != null) {
            return "Неудалось распознать купюру";
        }
        return "Купюра наминалом " + newBanknote.getNominal() + " принята";
    }

    private String validateSum(int sum) {
        if (sum < 0) {
            return "Сумма не может быть отрицательной";
        } else if (sum % 100 != 0) {
            return "Сумма для выдачи должна быть кратна 100";
        } else if (getBalace() < sum) {
            return "Запрошеная сумма превышает баланс банкомата";
        }
        return "";
    }

    public int getBalace() {
        return deposit.getBalance();
    }

    public DepositBox getDeposit() {
        return deposit;
    }

    public void setDeposit(DepositBox deposit) {
        this.deposit = deposit;
    }
}
