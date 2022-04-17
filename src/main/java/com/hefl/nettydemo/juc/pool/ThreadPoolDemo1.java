package com.hefl.nettydemo.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hefl
 * @date 2022/4/17 16:48
 * TODO 线程池三种分类
 */
public class ThreadPoolDemo1 {


    public static void main(String[] args) {
        // 一池五线程
//        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 一池一线程
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 一池可扩容线程
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            // 10个请求
            for (int i = 1; i <= 20; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+" 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        executorService.shutdown();
    }
}
