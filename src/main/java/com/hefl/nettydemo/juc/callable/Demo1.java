package com.hefl.nettydemo.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author hefl
 * @date 2022/4/17 14:21
 * TODO
 */
public class Demo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(new MyThead1()).start();

        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(new MyThead2());
        FutureTask<Integer> futureTask2 = new FutureTask<Integer>(() ->{
            System.out.println(Thread.currentThread().getName() + "task");
            return 1024;
        });

        new Thread(futureTask1).start();
        new Thread(futureTask2).start();
        while (!futureTask2.isDone()) {
            System.out.println("wait...");
        }
        futureTask1.get();
        System.out.println(futureTask1.get() + " ... end1");
        System.out.println(futureTask2.get() + " ... end2");

        System.out.println(Thread.currentThread().getName() + "come over");

    }
}

class MyThead1 implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}

class MyThead2 implements Callable {
    @Override
    public Integer call() throws Exception {
        return 200;
    }
}