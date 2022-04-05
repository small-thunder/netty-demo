package com.hefl.nettydemo.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hefl
 * @date 2022/4/4 20:42
 * TODO  Files 拷贝
 */
public class TestFilesCopy {

    public static void main(String[] args) throws IOException {
        String source = "D:\\download\\一般\\Snipaste-2.7.1-Beta-x64";
        String target = "D:\\download\\一般\\Snipaste-2.7.1-Beta-x64AABC";
        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                String targetName = path.toString().replace(source, target);
                if(Files.isDirectory(path)){
                    Files.createDirectories(Paths.get(targetName));
                }else if (Files.isRegularFile(path)){
                    Files.copy(path,Paths.get(targetName));
                }
            }catch (Exception e){
            }
        });
    }
}
