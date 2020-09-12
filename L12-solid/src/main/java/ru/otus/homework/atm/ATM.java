package ru.otus.homework.atm;

import ru.otus.homework.atm.deposit.CashMachine;

import java.util.Map;

public class ATM {
    private CashMachine cashMachine;

    public ATM(Map<Integer, Integer> depositBox) {
        this.cashMachine = new CashMachine(depositBox);
    }

    public String getMoney(int sum) {
        String mess = validateSum(sum);
        if (mess != null)
            return mess;

        Map<Integer, Integer> stackBanknotes = cashMachine.getCash(sum);
        if (stackBanknotes == null || stackBanknotes.isEmpty()) {
            return "Не возможно выдать запрошеную вами сумму";
        }

        return "Сумма " + calculateTheAmount(stackBanknotes) + " выдана";
    }

    public String getRemnantMoney() {
        Map<Integer, Integer> stackBanknotes = cashMachine.getCash(cashMachine.getBalace());
        return "Выдан остаток денежных средств в размере " + calculateTheAmount(stackBanknotes);
    }

    public String putMoney(Integer newBanknote) {
        Integer banknote2 = cashMachine.putBanknotes(newBanknote);
        if (banknote2 != null) {
            return "Неудалось распознать купюру";
        }
        return "Купюра наминалом " + newBanknote + " принята";
    }

    private int calculateTheAmount(Map<Integer, Integer> stackBanknotes) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : stackBanknotes.entrySet()) {
            sum += entry.getKey() * entry.getValue();
        }
        return sum;
    }

    private String validateSum(int sum) {
        if (sum < 0) {
            return "Сумма не может быть отрицательной";
        } else if (sum % 100 != 0) {
            return "Сумма для выдачи должна быть кратна 100";
        } else if (cashMachine.getBalace() < sum) {
            return "Запрошеная сумма превышает баланс банкомата";
        }
        return null;
    }

    public int getBalace(){
        return cashMachine.getBalace();
    }
}
