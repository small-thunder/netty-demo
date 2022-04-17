package com.hefl.nettydemo.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hefl
 * @date 2022/4/16 21:36
 * TODO
 */
public class LsaleTicket {


    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }).start();
    }
}

class Ticket {
    private int number = 10;

    private final ReentrantLock lock = new ReentrantLock(true);

    public void sale() {
        try {
            lock.lock();
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出" + number-- + " -- 剩余：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
