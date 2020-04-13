package com.java.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @ClassName FileDelete
 * @Description 描述
 * @Author LiYan
 * @Date 2020/4/9 21:01
 * @Version 1.0
 */
public class FileDelete  {
    public static void main(String[] args) {
        File file = new File("F:\\ApacheSoftware\\tomcat\\tomcat-A\\webapps\\poi\\images");

    }
    @Test
    public void test(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
    }

    /**
     * 删除某个文件夹中的所有文件： 删除图片
     * @param file
     */
    public static   void deleteFile(File file) {
        int flag = 1;
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            flag = 0;
            System.out.println("文件删除失败,请检查文件路径是否正确");
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f : files) {
            //打印文件名
            String name = file.getName();
            System.out.println(name);
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                deleteFile(f);
            } else {
                f.delete();
            }
        }
    }
}
