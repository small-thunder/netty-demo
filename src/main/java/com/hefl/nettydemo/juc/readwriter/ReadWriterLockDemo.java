package com.hefl.nettydemo.juc.readwriter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author hefl
 * @date 2022/4/17 15:40
 * TODO 读写锁
 */
public class ReadWriterLockDemo {

    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache();

        for (int i = 1; i < 5; i++) {
            final int num = i;
            new Thread(() ->{
                myCache.put(num + "",num);
            },String.valueOf(i)).start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < 4; i++) {
            final int num = i;
            new Thread(() ->{
                myCache.get(num + "");
            },String.valueOf(i)).start();
        }
    }
}

class MyCache {

    private volatile Map<String,Object> map = new HashMap<>();

    private ReadWriteLock rwLock = new ReentrantReadWriteLock(); // 读写锁对象

    public void put(String key ,Object value){
        // 添加写锁
        try {
            rwLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "正在写操作" + key);

            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            map.put(key,value);
            System.out.println(Thread.currentThread().getName() + "写完了" + key);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public Object get(String key){
        rwLock.readLock().lock(); // 读锁
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + "正在读取操作" + key);

            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "取完了" + key);

        } finally {
            rwLock.readLock().unlock();
        }
        return result;
    }
}
