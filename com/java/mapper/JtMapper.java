package com.java.mapper;

import com.java.pojo.Admin;
import com.java.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JtMapper {

    List<User> selectAll(Integer adminId);

    /**
     * 上传图片，把图片地址保存到数据库中，
     * 并且保存当前上传的时间
     * @param user
     * @return
     */
    int update(User user);


    int selectUserName(Map<String, Object> map);

    /**
     * 查询当前登录的管理员录入的所有人的数据
     * @param AdminId
     * @return
     */
    List<User> selectUser(@Param("AdminId") Integer AdminId);

    /**
     *  把管理员录入的信息，写入普通用户表中
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 获取总人数 完成人数 ，未完成人数
     * @param adminId
     * @return
     */
    List<Map<String, Object>> countUser(@Param("adminId") Integer adminId);

    /**
     * @return int
     * @Author LiYan
     * @Description 每周 星期天晚上23.58清空字段imageName
     * @Date 2020/4/10 9:25
     * @Param [adminId]
     **/
    int close();

    /**
     * @return java.util.List<java.lang.String>
     * @Author LiYan
     * @Description 获取未完成的人
     * @Date 2020/4/11 18:24
     * @Param [adminId]
     **/
    List<String> selectNotUserName(Integer adminId);


    /**
     * 获取Webhook地址
     * @param adminId 当前已登录的管理员ID
     * @return
     */
    String selectWebHookURL(Integer adminId);
}
