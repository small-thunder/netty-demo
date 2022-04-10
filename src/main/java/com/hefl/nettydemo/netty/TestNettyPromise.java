package com.hefl.nettydemo.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author hefl
 * @date 2022/4/10 20:51
 * TODO
 */
@Slf4j
public class TestNettyPromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 准备EventLoop对象
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 可以主动创建 promise 结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
           // 任意一个线程 执行计算  计算完毕后向promise 填充结果
            log.debug("开始计算");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            promise.setSuccess(100);
        }).start();

        log.debug("等待结果");
        log.debug("结果是" + promise.get());
    }
}
