package com.hefl.nettydemo.nio.bytebuffer;

import com.hefl.nettydemo.nio.common.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author hefl
 * @date 2022/4/4 20:42
 * TODO 转换
 */
public class TestByteBuffterString {

    public static void main(String[] args) {
        // 1.字符串转 ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("hello".getBytes());
        ByteBufferUtil.debugAll(buffer);

        // 2 Charset
        ByteBuffer hello = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(hello);

        // 3.wrap
        ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes());
        ByteBufferUtil.debugAll(wrap);

        // 4.转为字符串
        String hello1 = StandardCharsets.UTF_8.decode(hello).toString();
        System.out.println(hello1);

        buffer.flip();
        String buffer1 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(buffer1);

        String wrap1 = StandardCharsets.UTF_8.decode(wrap).toString();
        System.out.println(wrap1);

    }
}
