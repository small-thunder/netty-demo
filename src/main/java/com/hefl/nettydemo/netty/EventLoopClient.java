package com.hefl.nettydemo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author hefl
 * @date 2022/4/7 21:45
 * TODO
 */
@Slf4j
public class EventLoopClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 启动类
        ChannelFuture channelFuture = new Bootstrap()
                // 添加 EventLoop
                .group(group)
                // 选择客户端 channel
                .channel(NioSocketChannel.class)
                // 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override // 在连接建立后调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder()); // 把hello 转成ByteBuf
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));

        // 使用 sync 方法同步处理结果
        Channel channel = channelFuture.sync() // 阻塞方法，直到连接建立
                .channel();// 客户端与服务器的连接对象

        // 使用 addListener(回调对象) 方法异步处理结果
//        channelFuture.addListener(new ChannelFutureListener() {
//            @Override
//            // 在 nio 线程建立好之后 ， 调用
//            public void operationComplete(ChannelFuture future) throws Exception {
//                future.channel()
//                        .writeAndFlush("我是客户端 ");
//            }
//        });

        // 向服务器发送数据
        new Thread(() ->{
            Scanner scanner = new Scanner(System.in);
            while (true){
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    channel.close(); // close 异步操作 1s之后
                    break;
                }
                channel.writeAndFlush(line);
            }
        }, "input").start();

        // 获取closeFuture 对象 1）同步处理关闭  2）异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.sync();
        log.debug("处理关闭之后的操作");

//        closeFuture.addListener((ChannelFutureListener) future -> {
//            log.debug("处理关闭之后的操作");
//            group.shutdownGracefully();
//        });

    }
}
