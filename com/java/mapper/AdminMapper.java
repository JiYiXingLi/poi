package com.java.mapper;

import com.java.pojo.Admin;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AdminMapper
 * @Description 描述
 * @Author LiYan
 * @Date 2020/4/5 17:03
 * @Version 1.0
 */
public interface AdminMapper {

    /**
     *  根据当前登录的用户名查询数据库中对应的数据
     * @param Name
     * @return
     */
    Admin selectAdmin(String Name);

    /**
     * 查询所有注册过的班级信息
     * @return
     */
    List<Map<String, Object>> selectClassName();

    /**
     *  注册为管理员，默认状态为禁用
     * @param admin
     * @return
     */
    int insertAdmin(Admin admin);


}
