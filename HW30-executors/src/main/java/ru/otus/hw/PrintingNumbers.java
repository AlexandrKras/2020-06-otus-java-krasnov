package ru.otus.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintingNumbers {
    private static final Logger logger = LoggerFactory.getLogger(PrintingNumbers.class);

    private static final int max_number = 10;
    private static final int min_number = 1;

    private final Lock lock = new ReentrantLock();

    private int number = 1;
    private String last = "t2";

    public static void main(String[] args) throws InterruptedException {
        new PrintingNumbers().go();
    }

    private void go() throws InterruptedException {
        Thread t1 = new Thread(() -> print());
        t1.setName("t1");
        t1.start();

        Thread t2 = new Thread(() -> print());
        t2.setName("t2");
        t2.start();

        t1.join();
        t2.join();
    }

    private void print() {
        String operation = "increase";
        while (!Thread.currentThread().isInterrupted()) {
            String name = Thread.currentThread().getName();
            if (!name.equals(last)) {
                try {
                lock.lock();
                    logger.info("number: {}", number);
                    operation = getOperation(operation);
                    incrementedOrDecrementIfThreadT2(name, operation);
                    sleep(1);
                } finally {
                    lock.unlock();
                    last = name;
                }
            }
        }
    }

    private String getOperation(String currentOperation) {
        if (number == max_number) {
            return "decrease";
        } else if (number == min_number) {
            return "increase";
        }

        return currentOperation;
    }

    private void incrementedOrDecrementIfThreadT2(String nameThread, String operation) {
        if (nameThread.equals("t1")) {
            return;
        }

        if (operation.equals("increase")) {
            number++;
        } else {
            number--;
        }
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}