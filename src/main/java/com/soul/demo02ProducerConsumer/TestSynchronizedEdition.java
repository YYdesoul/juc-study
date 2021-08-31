package com.soul.demo02ProducerConsumer;

public class TestSynchronizedEdition {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.decrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.decrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "D").start();
    }
}

class Data {
    static int count = 0;

    public synchronized void increment() throws InterruptedException {
        while (count != 0) {
            this.wait();
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "=>" + count);
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while (count == 0) {
            this.wait();
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "=>" + count);
        this.notifyAll();
    }
}
