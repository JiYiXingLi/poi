package com.java.pojo;

import java.io.Serializable;

/**
 * @ClassName Admin
 * @Description &#x63cf;&#x8ff0;
 * @Author LiYan
 * @Date 2020/4/5 19:00
 * @Version 1.0
 */
public class Admin implements Serializable {
    private Integer id;
    private String Name;
    private String Pwd;
    private String ClassName;
    private  String WebHook;

    public String getWebHook() {
        return WebHook;
    }

    public void setWebHook(String webHook) {
        WebHook = webHook;
    }

    public Admin(Integer id, String name, String pwd, String className, String webHook) {
        this.id = id;
        Name = name;
        Pwd = pwd;
        ClassName = className;
        WebHook = webHook;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Pwd='" + Pwd + '\'' +
                ", ClassName='" + ClassName + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public Admin() {
    }
}
