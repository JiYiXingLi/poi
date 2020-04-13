package com.java.controller;

import com.java.pojo.User;
import com.java.service.JtService;
import com.java.utils.FileDelete;
import com.java.utils.Read;
import com.java.utils.Thumbnailator;
import com.java.utils.WebHook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName JtController
 * @Description 截图上传相关
 * @Author LiYan
 * @Date 2020/4/4 2:58
 * @Version 1.0
 */
@Controller
public class JtController {
    @Autowired
    private JtService jtService;
    @Autowired
    private HttpServletRequest request;
    Logger logger = LoggerFactory.getLogger(JtController.class);

    /**
     *
     * @param request
     * @return 把图片写入Excel表格中 返回文件名
     * @throws IOException
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request) throws IOException {
        logger.info("-----------------------开始写入----------");
        Session session = SecurityUtils.getSubject().getSession();
        if (session.getAttribute("id") == null) {
            return null;
        }

        int adminId = Integer.parseInt(session.getAttribute("id").toString());
        logger.info("-----------------------获取用户ID成功----------");
        try {
            return jtService.selectAll(adminId, request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    /**
     * 上传图片
     * @param userName 姓名
     * @param adminId 管理员ID
     * @param imageName 图片
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/insert")
    @ResponseBody
    public boolean insert(String userName, String adminId, @RequestParam("imageName") MultipartFile imageName, HttpServletRequest request) throws IOException {

        if (!this.isName(userName, adminId)) {
            return false;
        } else {
            User jt = new User();
            jt.setUserName(userName);
            String path = request.getServletContext().getRealPath("images");
            String fileName = imageName.getOriginalFilename();
            System.out.println("图片名称=" + fileName);
            String str = UUID.randomUUID().toString().substring(0, 8);
            /*判断上传的文件是否为图片*/
            if (fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("PNG") ||
                    fileName.endsWith("JPG")) {
                fileName = path + "/" + str + ".jpg";
                System.out.println(fileName);
                File file = new File(path, str + ".jpg");
                jt.setImageName(fileName);
                /*保存文件*/
                imageName.transferTo(file);
                /*压缩图片*/
                Thumbnailator.compressImage(path + "/" + str + ".jpg");
                /*把保存后的图片地址写入数据库中*/
                return this.jtService.update(jt);
            } else {
                return false;
            }
        }
    }

    /**
     * 这个是干嘛的，我忘记了
     * @param userName
     * @param adminId
     * @return
     */
    @RequestMapping("/isName")
    @ResponseBody
    public boolean isName(String userName, String adminId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("adminId", adminId);
        return jtService.isName(map);
    }

    /**
     * 跳转
     * @param AdminId
     * @return
     */
    @RequestMapping("/tz")
    public String tz(Integer AdminId) {
        return "/show";
    }


    /**
     * 返回当前登录的管理员的班级的所有人的所有数据
     * @return
     */
    @RequestMapping("/selectUser")
    @ResponseBody
    public List<User> selectUser() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Integer adminId = Integer.parseInt(session.getAttribute("id").toString());
        return jtService.selectUser(adminId);

    }

    /**
     * 当前已经登录的管理员向数据库中导入数据：读取Excel表格中的数据
     *
     * @param fileExcel
     * @param request
     * @return
     */
    @RequestMapping("/insertUser")
    @ResponseBody
    public boolean insertUser(@RequestParam("fileExcel") MultipartFile fileExcel, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String adminId = session.getAttribute("id").toString();
        String str = UUID.randomUUID().toString().substring(0, 8);
        String path = request.getServletContext().getRealPath("upload");
        String paths = path + "/" + str + ".xlsx";
        File file = new File(path, str + ".xlsx");
        List<User> userList = null;
        try {
            /*保存当前需要被读取的文件*/
            fileExcel.transferTo(file);
            /*将读取成功的数据封装到List<User>中*/
            userList = Read.read(paths);
            return jtService.insertUser(userList, adminId);

        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 获取当前已完成人数 未完成人数 总人数
     * @return
     */
    @RequestMapping("/countUser")
    @ResponseBody
    public List<Map<String, Object>> countUser() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Integer adminId = Integer.parseInt(session.getAttribute("id").toString());
        System.out.println(jtService.countUser(adminId));
        return jtService.countUser(adminId);
    }

    /**
     * 定时器 每周星期天晚上23.58分清空所有用户上传的图片地址和删除所有图片
     */
    @Scheduled(cron = "0 58 23 * * 7")
    @RequestMapping("/close")
    public void close() {
        System.out.println("定时器启动");
        try {
            jtService.close();
            FileDelete.deleteFile(new File("/www/server/tomcat/webapps/poi/images"));
            logger.info("---------------------定时器启动成功------------");
        } catch (Exception e) {
            logger.info("定时器异常");
            e.printStackTrace();
        }
    }


    /**
     * 在QQ群中发送消息;提示所有未完成的人，请完成截图
     * @return
     */
    @RequestMapping("/message")
    @ResponseBody
    public boolean message() {
        Session session = SecurityUtils.getSubject().getSession();
        Integer adminId = Integer.parseInt(session.getAttribute("id").toString());
        List<String> list = jtService.selectNotUserName(adminId);
        String url = jtService.selectWebHookURL(adminId);
        return WebHook.WebhookTools(url,list);
    }
}
