package com.ihrm.system;

import com.ihrm.common.exception.CommonException;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.UserDao;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.RoleService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author jack hao
 * @createTime 2020-06-10-3:07
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Test {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserDao userDao;

    @org.junit.Test
    public void test(){
        List<Role> all = roleService.findAll("1");
        for (Role role:all
             ) {
            System.out.println(role);
        }
    }

    @org.junit.Test
    public void test2() throws CommonException {
        Map map = permissionService.findById("id");
        System.out.println(map.toString());
    }

    @org.junit.Test
    public void test3(){
        User byMobile = userDao.findByMobile("13500000001");
        System.out.println(byMobile.getCreateTime());
    }
}
