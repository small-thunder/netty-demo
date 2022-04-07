package com.hefl.nettydemo.nio.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hefl
 * @date 2022/4/6 20:46
 * TODO
 */
public class MultiThread {

    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));

        // 创建固定数量worker 并初始化
        int cpuCount = Runtime.getRuntime().availableProcessors(); // 得到机器可用的 cpu 核心数
        Worker[] workers = new Worker[cpuCount];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-"+ i);
        }
//        Worker worker = new Worker("worker-0");

        AtomicInteger index = new AtomicInteger();
        while (true){
            boss.select();

            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

                    // 关联 selector
                    // round robin
                    workers[index.getAndIncrement() % workers.length].register(sc); // boss调用

                }
            }
        }

    }

    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();  //队列在2个线程之间通信
        private volatile boolean start = false; //还未初始化

        public Worker(String name) {
            this.name = name;
        }

        // 初始化线程 和 selector
        private void register(SocketChannel sc) throws IOException {
            if (!start){
                thread = new Thread(this, name);
                thread.start();
                selector = Selector.open();
                start = true;
            }
            // 向队列添加了任务，但这个任务并没有立即执行 boss
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });

            selector.wakeup(); // 唤醒select 方法

        }

        @Override
        public void run() {
            while (true){
                try {
                    selector.select(); // worker-0 阻塞   wakeup
                    Runnable task = queue.poll();
                    if (task != null){
                        task.run(); //执行了 sc.register(selector, SelectionKey.OP_READ, null);
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        sc.read(buffer);
                        buffer.flip();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
