package com.java.service.Impl;

import com.java.mapper.JtMapper;
import com.java.pojo.Admin;
import com.java.pojo.User;
import com.java.service.JtService;
import com.java.utils.FileDelete;
import com.java.utils.Write;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JtServiceImpl
 * @Description 描述
 * @Author LiYan
 * @Date 2020/4/4 2:57
 * @Version 1.0
 */
@Service
public class JtServiceImpl implements JtService {
    @Autowired
    private JtMapper jtMapper;

    @Override
    public String selectAll(Integer adminId, HttpServletRequest request) throws IOException {
        List<User> jtList = jtMapper.selectAll(adminId);

        return Write.write(jtList, request);
    }

    @Override
    public boolean update(User jt) {
        return jtMapper.update(jt) >= 1 ? true : false;
    }

    @Override
    public boolean isName(Map<String, Object> map) {
        Integer num = jtMapper.selectUserName(map);
        if (num >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> selectUser(Integer AdminId) {
        return jtMapper.selectUser(AdminId);
    }

    @Override
    public boolean insertUser(List<User> userList, String adminId) {
        try {
            for (User user : userList) {
                user.setAdminId(adminId);
                jtMapper.insert(user);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public List<Map<String, Object>> countUser(Integer adminId) {
        return jtMapper.countUser(adminId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean close() {
        if (jtMapper.close() >= 1 ? true : false) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> selectNotUserName(Integer adminId) {
        return jtMapper.selectNotUserName(adminId);
    }

    @Override
    public String selectWebHookURL(Integer adminId) {
        return jtMapper.selectWebHookURL(adminId);
    }

}
