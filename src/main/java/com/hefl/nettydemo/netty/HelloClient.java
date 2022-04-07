package com.hefl.nettydemo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author hefl
 * @date 2022/4/7 21:45
 * TODO
 */
public class HelloClient {

    public static void main(String[] args) throws InterruptedException {
        // 启动类
        new Bootstrap()
                // 添加 EventLoop
                .group(new NioEventLoopGroup())
                // 选择客户端 channel
                .channel(NioSocketChannel.class)
                // 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override // 在连接建立后调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder()); // 把hello 转成ByteBuf
                    }
                })
                .connect(new InetSocketAddress("localhost",8080))
                .sync() // 阻塞方法，直到连接建立
                .channel()  // 客户端与服务器的连接对象
                // 向服务器发送数据
                .writeAndFlush("你好  Hello world1");

    }
}
