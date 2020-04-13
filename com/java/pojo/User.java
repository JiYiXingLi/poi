package com.java.pojo;

/**
 * @AdminId Jt
 * @Description 描述
 * @Author LiYan
 * @Date 2020/4/4 2:54
 * @Version 1.0
 */
public class User {
    private String AdminId;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private String dateTime;
    private String ClassName;

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String ImageName) {
        this.ImageName = ImageName;
    }

    @Override
    public String toString() {
        return "Jt{" +
                "AdminId='" + AdminId + '\'' +
                ", id=" + id +
                ", UserName='" + UserName + '\'' +
                ", ImageName='" + ImageName + '\'' +
                '}';
    }

    public String getAdminId() {
        return AdminId;
    }

    public void setAdminId(String AdminId) {
        this.AdminId = AdminId;
    }

    private Integer id;

    public User() {
    }

    private String UserName;
    private String ImageName;


    public User(Integer id, String userName, String imageName, String AdminId, String dateTime) {
        this.AdminId = AdminId;
        this.id = id;
        this.UserName = userName;
        this.ImageName = imageName;
        this.dateTime = dateTime;
    }
}
