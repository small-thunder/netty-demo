package com.hefl.nettydemo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author hefl
 * @date 2022/4/7 19:59
 * TODO
 */
public class HelloServer {

    public static void main(String[] args) {
        new ServerBootstrap() // 启动器 负责组装netty组件，启动服务器
                .group(new NioEventLoopGroup()) // 组， selector   accept  read
                .channel(NioServerSocketChannel.class)   // 选择服务器的 ServerSocketChannel 实现
                .childHandler( // boss 负责连接  child 负责读写   决定了child 能执行哪些操作(handler)
                    new ChannelInitializer<NioSocketChannel>() {  // channel 代表和客户端进行数据读写的操作  Initializer 初始化，负责添加别的handler
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            // 添加具体 handler
                            ch.pipeline().addLast(new StringDecoder()); // 将ByteBuf 还原为字符串
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){  // 自定义 handler
                                @Override // 读事件
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    super.channelRead(ctx, msg);
                                    // 打印上一步转换好的字符串
                                    System.out.println("msg == " +msg);
                                }
                            });
                        }
                    })
                    .bind(8080);
    }

}
