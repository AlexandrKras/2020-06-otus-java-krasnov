package ru.otus.processor;

import ru.otus.Message;

import java.util.concurrent.TimeUnit;

public class ExceptionProcessor implements Processor {
    private Processor processor;

    public ExceptionProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) % 2 == 0)
            throw new RuntimeException("Метод вызван в четную секунду!!!");
        return processor.process(message);
    }
}
