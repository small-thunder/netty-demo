package com.hefl.nettydemo.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author hefl
 * @date 2022/4/17 14:53
 * TODO 减少计数
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "号同学离开教室");

                countDownLatch.countDown();  // -1

            }, String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println("班长走人");
    }
}
