package ru.otus;

public class History {

    public static String message2String(Message oldMsg, Message newMsg) {
        return String.format("oldMsg:%s,\r\nnewMsg:%s", oldMsg, newMsg);
    }
}
