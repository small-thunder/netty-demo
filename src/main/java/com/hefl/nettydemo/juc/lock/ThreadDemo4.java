package com.hefl.nettydemo.juc.lock;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author hefl
 * @date 2022/4/17 10:19
 * TODO List集合线程不安全
 */
public class ThreadDemo4 {

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        Vector<String> list = new Vector<>();

//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                list.add(UUID.randomUUID().toString().substring(0,8));

                System.out.println(list);
            }
        }).start();
    }
}
