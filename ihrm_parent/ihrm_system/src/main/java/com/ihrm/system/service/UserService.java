package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author jack hao
 * @createTime 2020-06-09-23:47
 */
@Service
public class UserService extends BaseService<User> {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 1.保存用户
     */
    public void add(@RequestBody User user){
        String id=idWorker.nextId()+"";
        user.setId(id);
        //设置默认密码
        user.setPassword("123456");
        //设置默认状态，默认状态为启用
        user.setEnableState(1);
        userDao.save(user);
    }
    /**
     * 2.更新用户
     */
    public void update(@RequestBody User user){
        User u = userDao.findById(user.getId()).get();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setEnableState(user.getEnableState());
        u.setFormOfManagement(user.getFormOfManagement());
        userDao.save(u);
    }
    /**
     * 3.根据id查询部门
     */
    public User findById(String id){
        return userDao.findById(id).get();
    }
    /**
     * 4.根据条件查询用户
     */
    public  Page<User> getAll(Map<String ,Object>map,Integer pageNum,Integer pageSize){
        Specification<User> spec=new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list=new ArrayList<>();
                //根据请求的companyid构造查询条件
                if(!StringUtils.isEmpty(map.get("companyId"))){
                    list.add(cb.equal(root.get("companyId").as(String.class),(String)map.get("companyId")));
                }
                //根据请求的departmentId构造查询条件
                if(!StringUtils.isEmpty(map.get("departmentId"))){
                    list.add(cb.equal(root.get("departmentId").as(String.class),(String)map.get("departmentId")));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //分页查询
        PageRequest pageRequest=new PageRequest(pageNum-1,pageSize);
        Page<User> allUser = userDao.findAll(spec, pageRequest);
        return allUser;
    }
    /**
     * 5.根据id删除部门
     */
    public void deleteById(String id){
        userDao.deleteById(id);
    }


    /**
     * 给用户分配角色
     */
    public void assignRole(String uid, List<String> list) {
        //1.根据id查询用户
        User user = userDao.findById(uid).get();
        //2.设置用户的角色集合
        Set<Role> roles=new HashSet<>();
        for(String roleId : list){
            Role role = roleDao.findById((roleId)).get();
            roles.add(role);
        }

        user.setRoles(roles);
        //3.更新用户
        userDao.save(user);
    }
}
