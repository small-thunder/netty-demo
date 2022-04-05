package com.hefl.nettydemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * capacity：缓冲区的容量。通过构造函数赋予，一旦设置，无法更改
 * limit：缓冲区的界限。位于limit 后的数据不可读写。缓冲区的限制不能为负，并且不能大于其容量
 * position：下一个读写位置的索引（类似PC）。缓冲区的位置不能为负，并且不能大于limit
 * mark：记录当前position的值。position被改变后，可以通过调用reset() 方法恢复到mark的位置。
 */
//@SpringBootTest
@Slf4j
class NettyDemoApplicationTests {

    @Test
    void contextLoads() {
        // FileChannel
        // 1.输入输出流 2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10); // 分配内存长度
            while (true){
                int len = channel.read(buffer);// 读取数据 写到buffer
                log.debug("读取到的字节数 {}", len);
                if(len == -1){
                    break;
                }
                buffer.flip(); // 切换至读模式
                while (buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.debug("实际的字节 {}", (char) b);
                }
                buffer.clear(); //切换为写模式
//                buffer.compact();
            }
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        // FileChannel
        // 1.输入输出流 2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(2); // 分配内存长度
            StringBuilder builder = new StringBuilder();
            while (true){
                int len = channel.read(buffer);// 读取数据 写到buffer
                log.debug("读取到的字节数 {}", len);
                if(len == -1){
                    break;
                }
                buffer.flip(); // 切换至读模式
                while (buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.debug("实际的字节 {}", (char) b);
                    builder.append((char)b);
                }
                buffer.clear(); //切换为写模式
            }
            System.out.println(builder.toString());
        } catch (IOException e) {
        }
    }

}
