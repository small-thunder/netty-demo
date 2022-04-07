package com.hefl.nettydemo.nio.bytebuffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author hefl
 * @date 2022/4/4 20:42
 * TODO 集中写
 */
public class TestGatheringWriter {

    public static void main(String[] args) {
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("你好");

        try (FileChannel channel = new RandomAccessFile("woeds2.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{buffer1,buffer2,buffer3});
        } catch (IOException e) {
        }

    }
}
