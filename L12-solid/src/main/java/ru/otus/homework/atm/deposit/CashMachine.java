package ru.otus.homework.atm.deposit;

import ru.otus.homework.atm.deposit.Banknote.IBanknote;
import ru.otus.homework.atm.deposit.Banknote.StackBanknotes;

public class CashMachine {
    private final DepositBox box5000;
    private final DepositBox box2000;
    private final DepositBox box1000;
    private final DepositBox box500;
    private final DepositBox box200;
    private final DepositBox box100;

    public CashMachine() {
        box5000 = new DepositBox5000();
        box5000.setCount(1);
        box2000 = new DepositBox2000();
        box2000.setCount(100);
        box1000 = new DepositBox1000();
        box1000.setCount(100);
        box500 = new DepositBox500();
        box500.setCount(100);
        box200 = new DepositBox200();
        box200.setCount(100);
        box100 = new DepositBox100();
        box100.setCount(100);

        box5000.setNext(box2000);
        box2000.setNext(box1000);
        box1000.setNext(box500);
        box500.setNext(box200);
        box200.setNext(box100);
    }

    public String getMoney(int sum) {
        String mess = validateSum(sum);
        StackBanknotes stackBanknotes = null;
        if (mess.isEmpty()) {
            stackBanknotes = box5000.getBanknotes(sum);
        } else {
            return mess;
        }

        if (stackBanknotes.getStackBanknotes().isEmpty()) {
            return "Не возможно выдать запрошеную вами сумму";
        }

        return "Сумма " + stackBanknotes.getSum() + " выдана";
    }

    public String getRemnantMoney() {
        StackBanknotes stackBanknotes = box5000.getBanknotes(getBalace());

        return "Выдан остаток денежных средств в размере " + stackBanknotes.getSum();
    }

    public String putMoney(IBanknote newBanknote) {
        IBanknote banknote = box5000.putBanknotes(newBanknote);
        if (banknote != null) {
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
        return box5000.getBalance();
    }

    public DepositBox getBox5000() {
        return box5000;
    }

    public DepositBox getBox2000() {
        return box2000;
    }

    public DepositBox getBox1000() {
        return box1000;
    }

    public DepositBox getBox500() {
        return box500;
    }

    public DepositBox getBox200() {
        return box200;
    }

    public DepositBox getBox100() {
        return box100;
    }
}
