package com.java.service.Impl;

import com.java.mapper.AdminMapper;
import com.java.pojo.Admin;
import com.java.service.AdminService;
import com.java.utils.SHA256Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AdminServiceImpl
 * @Description 描述
 * @Author LiYan
 * @Date 2020/4/5 17:07
 * @Version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin selectAdmin(String Name) {
        System.out.println(adminMapper.selectAdmin(Name));
        return adminMapper.selectAdmin(Name);
    }

    @Override
    public List<Map<String, Object>> selectClassName() {
        return adminMapper.selectClassName();
    }

    @Override
    public boolean insertAdmin(Admin admin) {
        admin.setPwd(SHA256Tools.SHA256(admin.getPwd(), admin.getName()));
        return adminMapper.insertAdmin(admin) >= 1 ? true : false;
    }
}
