package ru.otus.homework.calssfortest;

public class StringUtil {
    private final char[] cifra = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    public String giveOnlyNumbers(String str) {
        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (checkInCharacters(str.charAt(i), cifra))
                newStr += str.charAt(i);
        }
        return newStr;
    }

    public String createShortName(String surname, String name, String patronymic) {
        if (surname != null && name != null && patronymic != null)
            return surname.substring(0, 1).toUpperCase() + surname.substring(1) + " " + getInitial(name) + getInitial(patronymic);
        return "";
    }

    private String getInitial(String str) {
        if (str != null && !str.isEmpty())
            return str.trim().substring(0, 1).toUpperCase() + ".";
        return "";
    }

    private boolean checkInCharacters(char ch1, char[] array) {
        for (int i = 0; i < array.length; i++)
        {
            if (ch1 == array[i])
                return true;
        }
        return false;
    }
}
