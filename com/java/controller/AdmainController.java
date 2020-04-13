package com.java.controller;

import com.java.pojo.Admin;
import com.java.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AdmainController
 * @Description 描述
 * @Author LiYan
 * @Date 2020/4/5 17:01
 * @Version 1.0
 */
@RestController
public class AdmainController {
    Logger logger = LoggerFactory.getLogger(AdmainController.class);

    @Autowired
    private AdminService adminService;

    /**
     * 用户登录
     * @param Name
     * @param Pwd
     * @return
     */
    @RequestMapping("/login")
    public boolean login(String Name, String Pwd) {
        logger.info("开始登录");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(Name, Pwd);
        logger.info("当前登录的用户名为" + Name + "输入的密码为" + Pwd);
        try {
            subject.login(token);
            logger.info("登录成功");
        } catch (AuthenticationException e) {
            logger.info("登录失败");
            e.printStackTrace();
            return false;
        }
        Admin admin = adminService.selectAdmin(Name);
        Session session = subject.getSession();
        logger.info("开始保存当前用户ID");
        session.setAttribute("id", admin.getId());
        logger.info("用户ID保存成功");
        return true;
    }

    /**
     * 获取班级名
     * @return
     */
    @RequestMapping("/ClassName")
    public List<Map<String, Object>> ClassName() {
        return adminService.selectClassName();
    }

    /**
     * 用户注册
     * @param admin
     * @return
     */
    @RequestMapping("/Register")
    @ResponseBody
    public boolean Register(Admin admin) {
        return adminService.insertAdmin(admin);
    }

}
