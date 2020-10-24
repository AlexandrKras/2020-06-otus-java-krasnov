package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerHistory;
import ru.otus.listener.ListenerPrinter;
import ru.otus.processor.ExceptionProcessor;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorField11SwapField13;
import ru.otus.processor.ProcessorUpperField10;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13
       2. Сделать процессор, который поменяет местами значения field11 и field13
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду
       4. Сделать Listener для ведения истории: старое сообщение - новое
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */

        var processors = List.of(new ProcessorField11SwapField13(),
                new ProcessorConcatFields(),
                new ExceptionProcessor(new ProcessorUpperField10(), new TimeProvider() {
                    @Override
                    public long getCurrentTime() {
                        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                    }
                }));

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            System.out.println(ex.getMessage());
        });
        var listenerPrinter = new ListenerPrinter();
        var listenerHistory = new ListenerHistory();
        complexProcessor.addListener(listenerPrinter);
        complexProcessor.addListener(listenerHistory);

        var message = new Message.Builder()
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13("field13")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
        complexProcessor.removeListener(listenerHistory);
    }
}
