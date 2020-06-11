package com.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.jupiter.api.Test;

/**
 * @author jack hao
 * @createTime 2020-06-11-4:21
 */
public class ShiroTest {
    public static void main(String[] args) {
       ShiroTest st=new ShiroTest();
       st.realmTest();
    }


    public void loginTest(){
        //1.根据配置文件创建SecurityManagerFactory
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.通过工厂获取securityManager
        SecurityManager sm = factory.getInstance();
        //3.将securityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(sm);
        //4.从当前运行环境构造subject
        Subject subject = SecurityUtils.getSubject();
        //5.构造登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangsan", "123");
        //6.登录
        subject.login(usernamePasswordToken);

        //获取登录数据
        System.out.println("是否登录成功："+subject.isAuthenticated());
        System.out.println(subject.getPrincipal());
    }

    public void permission(){
        //1.根据配置文件创建SecurityManagerFactory
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.通过工厂获取securityManager
        SecurityManager sm = factory.getInstance();
        //3.将securityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(sm);
        //4.从当前运行环境构造subject
        Subject subject = SecurityUtils.getSubject();
        //5.构造登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("lisi", "456");
        //6.登录
        subject.login(usernamePasswordToken);

        //检验当前用户是否具有操作权限
        System.out.println(subject.hasRole("role2"));
        System.out.println(subject.isPermitted("user:save"));

    }

    public void realmTest(){
        //1.根据配置文件创建SecurityManagerFactory
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        //2.通过工厂获取securityManager
        SecurityManager sm = factory.getInstance();
        //3.将securityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(sm);
        //4.从当前运行环境构造subject
        Subject subject = SecurityUtils.getSubject();
        //5.构造登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("lisi", "123");

        //6.登录-->调用realm中的认证方法
        subject.login(usernamePasswordToken);

       //检验当前用户是否具有操作权限---->调用realm中的授权方法
        System.out.println(subject.hasRole("role2"));
        System.out.println(subject.isPermitted("user:save"));
    }
}
