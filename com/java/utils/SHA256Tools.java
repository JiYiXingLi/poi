package com.java.utils;

import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * @ClassName MD5Tools
 * @Description 描述
 * @Author LiYan
 * @Date 2020/3/23 14:07
 * @Version 1.0
 */
public class SHA256Tools {
    /**
     * 对当前密码进行加密操作
     * @param password 明文密码
     * @param salt  盐值
     * @return
     */
    public static String SHA256(String password, String salt) {
        String pwd = new Sha256Hash(password, salt, 10000).toBase64();
        return pwd;
    }

    public static void main(String[] args) {
        System.out.println(SHA256("1234", "新一"));
    }

}
