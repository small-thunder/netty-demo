package com.hefl.nettydemo.nio.bytebuffer;

import java.nio.ByteBuffer;

/**
 * @author hefl
 * @date 2022/4/4 20:42
 * TODO 读
 */
public class TestByteBuffterRead {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'}); //a
//        debugAll(buffer);
        buffer.flip();

//        buffer.get(new byte[4]);
//        debugAll(buffer);
//        buffer.rewind(); //从头开始读
//        System.out.println((char)buffer.get());

        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.mark(); // 加标记 索引为2 的位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.reset();  // 将position 重置到索引为2 的位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());

        System.out.println((char)buffer.get(3)); // 不会改变 读索引的位置
    }
}
