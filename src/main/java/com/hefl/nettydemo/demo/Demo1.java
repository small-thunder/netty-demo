package com.hefl.nettydemo.demo;

public class Demo1 {
 
    public static void main(String[] args){
        MySucrity mySucrity = new MySucrity();
        new Thread(mySucrity).start();
        new Thread(mySucrity).start();
        new Thread(mySucrity).start();
 
 
    }
    static class MySucrity implements Runnable{
 
        private int count=10;
        private Object o = new Object();
        @Override
        public void run() {
           while(true){
               synchronized(o){
                   if(count>0){
                       System.out.println("正在输出");
                       try {
                           Thread.sleep(1000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       count--;
                       System.out.println(Thread.currentThread().getName()+"余票："+count);
                   }else{
                       break;
                   }
               }
 
           }
 
        }
    }
}