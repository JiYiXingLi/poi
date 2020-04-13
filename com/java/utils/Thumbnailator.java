package com.java.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName Thumbnailator
 * @Description 图片压缩
 * @Author LiYan
 * @Date 2020/4/10 21:17
 * @Version 1.0
 */

public class Thumbnailator {
    public static void compressImage(String path) {
        try {
            System.out.println(path);
            File file = new File(path);
            if (file.length() >= 524288) {
                Thumbnails.Builder<File> of = Thumbnails.of(file);
                System.out.println("文件过大,执行压缩");
                of.scale(0.8f);
                of.toFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
