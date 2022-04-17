package com.hefl.nettydemo.juc.sync;

/**
 * @author hefl
 * @date 2022/4/16 21:54
 * TODO
 */
public class ThreadDemo1 {

    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
class Share{
    private int number = 0;
    private int count = 0;
    // +1
    public synchronized void incr() throws InterruptedException {
        while(number != 0){
            this.wait();
        }
        number ++;
        System.out.println(Thread.currentThread().getName()+"--" +number+"-- count"+ count ++);
        this.notifyAll();
    }
    // -1
    public synchronized void decr() throws InterruptedException {
        while (number != 1){
            this.wait();
        }
        number --;
        System.out.println(Thread.currentThread().getName()+"--" +number);
        this.notifyAll();
    }
}
