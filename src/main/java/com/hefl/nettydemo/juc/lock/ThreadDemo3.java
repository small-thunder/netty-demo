package com.hefl.nettydemo.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hefl
 * @date 2022/4/17 9:46
 * TODO
 */
public class ThreadDemo3 {

    public static void main(String[] args) {
        ShareResources shareResources = new ShareResources();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResources.print5(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
      new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResources.print10(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
      new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResources.print15(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class ShareResources {
    private int flag = 1;
    private Lock lock = new ReentrantLock();

    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(long loop) {
        lock.lock();
        try {
            while (flag != 1) {
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() +": " +i + " 轮数" + loop);
            }
            flag = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print10(long loop) {
        lock.lock();
        try {
            while (flag != 2) {
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() +": " + i+" 轮数" + loop);
            }
            flag = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print15(long loop) {
        lock.lock();
        try {
            while (flag != 3) {
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() +": " + i+ " 轮数" + loop);
            }
            flag = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}