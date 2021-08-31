package com.soul.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicketLockDemo02 {
    public static void main(String[] args) {
        Ticket2 ticket = new Ticket2();
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale(); }, "A").start();
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale(); }, "B").start();
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale(); }, "C").start();
    }
}

/**
 * Lock三个步骤
 * 1. new ReentratLock();
 * 2. lock.lock(); 加锁
 * 3. finally里面 lock.unlock(); 解锁
 */
class Ticket2 {
    static int count = 30;

    Lock lock = new ReentrantLock();

    // Lock锁
    public void sale() {
        lock.lock();
        try {
            if (count > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了" + (count--) + "票，剩余: " + count);
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
