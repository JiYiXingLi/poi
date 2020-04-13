package com.java.realm;

import com.java.pojo.Admin;
import com.java.service.AdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MyRealm
 * @Description 描述
 * @Author LiYan
 * @Date 2020/3/16 9:11
 * @Version 1.0
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private AdminService adminService;

    /**
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @Author LiYan
     * @Description ：
     * 作用：查询权限信息，并返回即可，不用任何比对
     * 何时触发：/user/query = roles["admin] /user/insert = perms["user:insert"]
     * 查询方式：通过用户名查询，该用户的 权限、角色 、信息
     * @Date 2020/3/16 9:12
     * @Param [principalCollection]
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户的用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        //查询当前用户的所有权限信息
        //RoleService roleService = ContextLoader.getCurrentWebApplicationContext().getBean("roleServiceImpl", RoleService.class);
        // PermissionService permissionService = ContextLoader.getCurrentWebApplicationContext().getBean("permissionServiceImpl", PermissionService.class);
      /*  Set<String> roles = roleService.queryAllRolenameByUsername(username);
        Set<String> perms = permissionService.queryAllPermissionByusername(username);*/

        //将查询出的信息 封装
       /* SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);

        simpleAuthorizationInfo.setStringPermissions(perms);*/
//        return simpleAuthorizationInfo;
        return null;
    }

    /**
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @Author LiYan
     * @Description :
     * 作用：查询身份信息，并返回即可，不用任何比对
     * 查询方式:通过用户名查询
     * 何时触发：subject.login(token);
     * @Date 2020/3/16 9:16
     * @Param [authenticationToken]
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("--------------------------------普通用户认证开始(MyRealm)--------------------------------------");
        //获取用户名
        String username = (String) token.getPrincipal();
        System.out.println("-------------------------");
        System.out.println(adminService.selectAdmin(username));
        System.out.println("-------------------------");
        //查询用户信息
        Admin admin = adminService.selectAdmin(username);
        System.out.println(adminService.selectAdmin(username));
        //用户名不存在
        if (admin == null) {
            System.out.println(username + "用户名不存在");
            //在后续流程中抛出异常
            return null;
        }

        //将用户信息封装在AuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(admin.getName(),
                        admin.getPwd(),
                        ByteSource.Util.bytes(username),
                        this.getName());
        System.out.println("--------------------------------普通用户认证结束(MyRealm)--------------------------------------");

        return simpleAuthenticationInfo;
    }
}
