package com.soul.demo01;

public class SaleTicketSynchronizedDemo01 {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        }, "C").start();
    }

}

class Ticket {
    static int count = 50;

    // Lock锁
    public synchronized void sale() {
        if (count > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出了" + (count--) + "票，剩余: " + count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}