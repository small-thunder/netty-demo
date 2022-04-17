package com.hefl.nettydemo.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author hefl
 * @date 2022/4/17 14:06
 * TODO 死锁
 */
public class DeadLock {

    static Object a = new Object();
    static Object b = new Object();

    public static void main(String[] args) {
        new Thread(() ->{
            synchronized (a){
                System.out.println(Thread.currentThread().getName()+"持有锁a ,试图获取锁b");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (b){
                    System.out.println("获取锁b");
                }
            }
        }).start();

        new Thread(() ->{
            synchronized (b){
                System.out.println(Thread.currentThread().getName()+"持有锁b ,试图获取锁a");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){
                    System.out.println("获取锁a");
                }
            }
        }).start();
    }
}
