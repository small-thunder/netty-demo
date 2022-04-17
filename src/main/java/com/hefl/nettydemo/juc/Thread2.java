package com.hefl.nettydemo.juc;

/**
 * @author hefl
 * @date 2022/4/16 20:49
 * TODO
 */
public class Thread2 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "--" + Thread.currentThread().isDaemon());
//            while (true) {
//
//            }
        });
//        thread.setDaemon(true);
        thread.start();
        System.out.println(Thread.currentThread().getName() + "");
    }
}
