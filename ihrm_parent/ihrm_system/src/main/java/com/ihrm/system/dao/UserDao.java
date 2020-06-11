package com.ihrm.system.dao;

import com.ihrm.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jack hao
 * @createTime 2020-06-09-23:46
 */
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    public User findByMobile(String mobile);
}
