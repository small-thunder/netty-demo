package com.hefl.nettydemo.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author hefl
 * @date 2022/4/5 20:51
 * TODO
 */
public class WriterClient {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));

        // 接收数据
        int count = 0;
        while (true){
            ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
            count += sc.read(buffer);
            System.out.println(count);
            buffer.clear();
        }
    }
}
