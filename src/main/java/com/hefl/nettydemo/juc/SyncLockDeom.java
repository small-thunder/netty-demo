package com.hefl.nettydemo.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hefl
 * @date 2022/4/17 13:54
 * TODO 可重入锁
 */
public class SyncLockDeom {

    public static void main(String[] args) {
//        Object o = new Object();
//        new Thread(() -> {
//            synchronized (o){
//                System.out.println(Thread.currentThread().getName()+"外层");
//                synchronized (o){
//                    System.out.println(Thread.currentThread().getName()+"中层");
//                    synchronized (o){
//                        System.out.println(Thread.currentThread().getName()+"内层");
//                    }
//                }
//            }
//        }).start();

        Lock lock = new ReentrantLock();
        new Thread(() -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"外层");
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName()+"内层");
                } finally {
                    lock.unlock();
                }

            } finally {
                lock.unlock();
            }
        }).start();
    }
}
