package com.hefl.nettydemo.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @author hefl
 * @date 2022/4/9 10:21
 * TODO
 */
@Slf4j
public class TestEventLoop {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(2); // io事件 普通任务，定时任务
        // 获取下个事件循环对象

//        System.out.println(group.next());
//        System.out.println(group.next());
//        System.out.println(group.next());



        // 执行普通任务
//        group.next().execute(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("ok");
//            log.debug("ok");
//        });
//        System.out.println("main");
//

        // 执行定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.debug("ok");
        }, 0  ,1 , TimeUnit.SECONDS); // 0 立即执行  1 1s执行一次

        log.debug("main");

//        System.out.println(NettyRuntime.availableProcessors()); // 核心数
    }
}
