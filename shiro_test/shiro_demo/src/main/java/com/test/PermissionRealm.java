package com.test;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义realm
 * @author jack hao
 * @createTime 2020-06-11-5:00
 */
public class PermissionRealm extends AuthorizingRealm {
    /**
     * 自定义realm名
     */
    public void setName(String name){
        super.setName("PermissionRealm");
    }

    public PermissionRealm(){
        super.setCachingEnabled(false);
    }

    //授权:根据认证的数据获取权限信息
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取安全数据  username 用户id
        String username = (String) principalCollection.getPrimaryPrincipal();
        //2.根据id或者用户吗查询用户(在此跳过,真实项目中需要查询数据库)
        //3.查询用户的角色和全权限信息(在此跳过 构造假数据)
        List<String> perms=new ArrayList<String>();
        List<String> roles=new ArrayList<String>();

        perms.add("user:save");
        perms.add("user:delete");

        roles.add("role2");
        //4.构造返回
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        //设置权限集合
        simpleAuthorizationInfo.addStringPermissions(perms);
        //设置角色集合
        simpleAuthorizationInfo.addRoles(roles);
        return simpleAuthorizationInfo;
    }

    //认证:主要目的是比较用户名和密码是否与数据库中的一致
    //认证成功，将安全数据保存到shiro进行保管
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.构造 usernamepasswordToken
        UsernamePasswordToken upToken= (UsernamePasswordToken) authenticationToken;
        //2.获取用户输入的用户名和密码
        String username=upToken.getHost();
        String password=new String(upToken.getPassword());
        //3.通过用户名获取数据库中的用户数据
        //4.比较获取的用户名和密码是否与数据库中的一致
        if("123".equals(password)){
            //5.如果成功，将安全数据存入shiro
            SimpleAuthenticationInfo simpleAuthorizationInfo=new SimpleAuthenticationInfo(username,password,getName());//1.安全数据  2.密码  3.当前realm的名称
        }else{
            //6.如果失败抛出异常，或返货null
            throw new RuntimeException("用户名或密码错误");
        }

       return null;
    }
}
