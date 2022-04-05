package com.hefl.nettydemo.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import static com.hefl.nettydemo.common.ByteBufferUtil.debugAll;

/**
 * @author hefl
 * @date 2022/4/4 20:42
 * TODO 转换
 */
public class TestFileChannelTransferTo {

    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("data.txt").getChannel();
                FileChannel to = new FileOutputStream("to.txt").getChannel();
        ) {
            long size = from.size();
            for (long left = size; left > 0;){
                System.out.println("size " + (size-left) + " left:"+ left);
                left -= from.transferTo((size - left), left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
