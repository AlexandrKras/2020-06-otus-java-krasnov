package ru.otus.listener;

import ru.otus.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class ListenerHistory implements Listener {
    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        try {
        Path path = Paths.get("L14-structuralPatterns/resources/history.txt");
        if (!Files.exists(path))
            Files.createFile(path);
        String logString = String.format("oldMsg:%s,\r\nnewMsg:%s", oldMsg, newMsg);

            Files.write(path, logString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
