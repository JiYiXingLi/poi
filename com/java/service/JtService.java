package com.java.service;

import com.java.pojo.Admin;
import com.java.pojo.User;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface JtService {
    /**
     * @return boolean
     * @Author LiYan
     * @Description 导出为Excel文件
     * @Date 2020/4/4 16:31
     * @Param [session]
     **/
    String selectAll(Integer adminId, HttpServletRequest request) throws IOException;

    /**
     * @return boolean
     * @Author LiYan
     * @Description 插入姓名和截图地址
     * @Date 2020/4/4 16:30
     * @Param [jt]
     **/
    boolean update(User jt);


    boolean isName(Map<String, Object> map);

    List<User> selectUser(@Param("AdminId") Integer AdminId);


    boolean insertUser(List<User> userList, String adminId);

    List<Map<String, Object>> countUser(@Param("adminId") Integer adminId);


    boolean close();


    List<String> selectNotUserName(Integer adminId);

    String selectWebHookURL(Integer adminId);
}
