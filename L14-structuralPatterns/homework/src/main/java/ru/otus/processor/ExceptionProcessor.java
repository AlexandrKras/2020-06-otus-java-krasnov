package ru.otus.processor;

import ru.otus.Message;
import ru.otus.TimeProvider;

public class ExceptionProcessor implements Processor {
    private Processor processor;
    private TimeProvider timeProvider;

    public ExceptionProcessor(Processor processor, TimeProvider timeProvider) {
        this.processor = processor;
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        if (timeProvider.getCurrentTime() % 2 == 0)
            throw new RuntimeException("Метод вызван в четную секунду!!!");
        return processor.process(message);
    }
}
