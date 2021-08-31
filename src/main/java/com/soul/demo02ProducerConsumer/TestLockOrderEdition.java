package com.soul.demo02ProducerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：多线程之间按顺序调用，实现 A->B->C *
 * 三个线程启动，要求如下： * AA 打印5次，BB 打印10次。CC打印15次，依次循环
 * * 重点：标志位
 */
public class TestLockOrderEdition {
    public static void main(String[] args) {
        Resources resources = new Resources();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resources.print5();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {

                resources.print10();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resources.print15();
            }
        }, "CC").start();
    }
}

class Resources { // 资源类
    private int number = 1; // 1A 2B 3C
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try { // 判断
            while (number != 1) {
                condition1.await();
            } // 干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + '\t' + i);
            } // 通知,指定的干活！
            number = 2; condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try { // 判断
            while (number != 2) {
                condition2.await();
            } // 干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + '\t' + i);
            } // 通知,指定的干活！
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try { // 判断
            while (number != 3) {
                condition3.await();
            } // 干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + '\t' + i);
            } // 通知,指定的干活！
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}