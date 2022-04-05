package com.hefl.nettydemo.bytebuffer;

import java.nio.ByteBuffer;

import static com.hefl.nettydemo.bytebuffer.ByteBufferUtil.debugAll;

/**
 * @author hefl
 * @date 2022/4/4 20:42
 * TODO 读写
 */
public class TestByteBuffterReadWriter {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61); //a
        debugAll(buffer);
        buffer.put(new byte[]{0x62,0x63,0x64}); //b c d
        debugAll(buffer);

        buffer.flip();
        System.out.println((char)buffer.get());
        debugAll(buffer);

        buffer.clear();
        debugAll(buffer);

        System.out.println(ByteBuffer.allocate(10).getClass());
        System.out.println(ByteBuffer.allocateDirect(10).getClass());
    }
}
