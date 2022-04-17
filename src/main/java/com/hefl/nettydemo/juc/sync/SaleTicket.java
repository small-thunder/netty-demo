package com.hefl.nettydemo.juc.sync;

/**
 * @author hefl
 * @date 2022/4/16 21:08
 * TODO
 */
public class SaleTicket {

    public static void main(String[] args) {
        ticket ticket = new ticket();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        },"aa").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        },"bb").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        },"cc").start();
    }
}


class ticket{
    private int number = 30;
    synchronized void sale(){
        if (number > 0) {
            System.out.println(Thread.currentThread().getName()+": 卖出" +number-- + "---- 剩下："+number);
        }
    }
}