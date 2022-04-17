package com.hefl.nettydemo.demo;

public class Demo2 {
    public static void main(String[] args){
        Mysucrity mySucrity = new Mysucrity();
        new Thread(mySucrity).start();
        new Thread(mySucrity).start();
        new Thread(mySucrity).start();
    }
    static class Mysucrity implements Runnable {
        private int count = 10;
 
 
        @Override
        public void run() {
            while(true){
                boolean sale = sale();
                if(!sale){
                    break;
                }
            }
        }
 
        public synchronized boolean sale() {
            if (count > 0) {
                System.out.println("正在输出");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
                System.out.println(Thread.currentThread().getName() + "余票：" + count);
                return true;
            }else{
                return false;
            }
       
        }
 
 
    }
}