package com.hefl.nettydemo.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author hefl
 * @date 2022/4/17 15:17
 * TODO 七龙珠集齐召唤神龙
 */
public class CyclicBarrierDemo {

    private static final int number = 7;
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrierDemo = new CyclicBarrier(number,() -> {
            System.out.println(" **** 龙珠集齐");
        });
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "龙珠到了");
                    cyclicBarrierDemo.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }
}
