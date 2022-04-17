package com.hefl.nettydemo.juc.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author hefl
 * @date 2022/4/17 16:48
 * TODO 自定义线程池创建
 */
public class ThreadPoolDemo2 {
    /**
     * 自定义线程名称,方便的出错的时候溯源
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("OGW-POOL-%d").build();


    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                3,
                5,
                3,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(3),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );

        try {
            // 10个请求
            for (int i = 1; i <= 10; i++) {
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+" 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

//        threadPoolExecutor.shutdown();

    }
}
