package com.hefl.nettydemo.nio.bytebuffer;

import java.nio.ByteBuffer;

import static com.hefl.nettydemo.nio.common.ByteBufferUtil.debugAll;

/**
 * @author hefl
 * @date 2022/4/4 20:42
 * TODO 黏包 半包
 */
public class TestByteBuffterExam {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("Hello,world \n I'm zs \n Ho".getBytes());
        split(buffer);
        buffer.put("w are you \n".getBytes());
        split(buffer);
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
