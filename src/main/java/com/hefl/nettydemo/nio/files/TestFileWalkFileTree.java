package com.hefl.nettydemo.nio.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hefl
 * @date 2022/4/4 20:42
 * TODO 删除 Files
 */
public class TestFileWalkFileTree {


    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("D:\\develop\\set\\java\\jdk8\\lib - 副本"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    public static void m1() throws IOException {
        AtomicInteger dirCount = new AtomicInteger(); // 文件夹
        AtomicInteger fileCount = new AtomicInteger(); // 文件
        Files.walkFileTree(Paths.get("D:\\develop\\set\\java\\jdk8"), new SimpleFileVisitor<Path>(){
            // 进入目录之前
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith("1")) {
                }
                // 进入目录
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

            // 退出
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return super.postVisitDirectory(dir, exc);
            }
        });
        System.out.println(dirCount);
        System.out.println(fileCount);

    }
}
