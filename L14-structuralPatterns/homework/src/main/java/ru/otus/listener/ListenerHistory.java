package ru.otus.listener;

import jdk.dynalink.StandardOperation;
import ru.otus.History;
import ru.otus.Message;

import javax.net.ssl.StandardConstants;
import javax.tools.StandardJavaFileManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ListenerHistory implements Listener {
    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        try {
            Path path = Files.createTempFile("structuralPatternsHistory", ".tmp");
            History history = new History(oldMsg.toString(), newMsg.toString());
            Files.write(path, history.stringForHistory().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
