package com.hefl.nettydemo.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.hefl.nettydemo.common.ByteBufferUtil.debugAll;
import static com.hefl.nettydemo.common.ByteBufferUtil.debugRead;

/**
 * @author hefl
 * @date 2022/4/5 13:54
 * TODO
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        // 1.创建selector 管理多个channel
        Selector selector = Selector.open();
        // 2.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); // 非阻塞模式

        // 3.建立selector与channel 的联系(注册)
        // SelectionKey 就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // key只关注 accept 事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        // 4.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        while (true){
            // 5.select 方法 没有事件发生，线程阻塞  有事件，线程恢复运行
            selector.select();

            // 6.处理事件，SelectionKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                log.debug("key: {}",key);
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    // 第一个byteBuffer 作为附件关联到 selectionKey上
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);

                }else if (key.isReadable()){
                    try {
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 获取selectionKey上 关联的附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = sc.read(buffer); // 如果正常断开 read = -1
                        if (read == -1){
                            key.cancel();
                        }else {
                            split(buffer);
                            System.out.println(Charset.defaultCharset().decode(buffer).toString());
                            if(buffer.position() == buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() *2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }


            }

        }
    }

    private static void split(ByteBuffer buffer) {
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            if (buffer.get(i) == '\n') {
                int len = i + 1 - buffer.position();
                ByteBuffer buffer1 = ByteBuffer.allocate(len);
                for (int j = 0; j < len; j++) {
                    buffer1.put(buffer.get());
                }
                debugAll(buffer1);
            }
        }

        buffer.compact(); //向左移动覆盖
    }
}
