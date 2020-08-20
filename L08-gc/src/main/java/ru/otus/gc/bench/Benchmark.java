package ru.otus.gc.bench;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            Object[] array = new Object[local];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            Thread.sleep(10); //Label_1
        }
    }

    public int run1Minute() throws InterruptedException {
        int cycle = 0;
        long start = System.currentTimeMillis();
        while (((System.currentTimeMillis() - start) / 1000) < 60) {
            cycle++;
            int local = size;
            Object[] array = new Object[local];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
        }
        return cycle;
    }

    public void runOutOfMemory() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Object> list = new ArrayList<>();
        while (((System.currentTimeMillis() - start) / 1000) < 1000) {
            int local = size;
            Object[] array = new Object[size];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            list.addAll(Arrays.asList(Arrays.copyOfRange(array, 0, 50000)));
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }
}
