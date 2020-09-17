package ru.otus;

public class History {
    private final String oldMsg;
    private final String newMsg;

    public History(String oldMsg, String newMsg) {
        this.oldMsg = oldMsg;
        this.newMsg = newMsg;
    }

    public String stringForHistory() {
        return String.format("oldMsg:%s,\r\nnewMsg:%s", oldMsg, newMsg);
    }

    public String getNewMsg() {
        return newMsg;
    }

    public String getOldMsg() {
        return oldMsg;
    }
}
