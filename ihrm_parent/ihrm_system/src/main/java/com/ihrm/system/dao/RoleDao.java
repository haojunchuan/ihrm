package com.ihrm.system.dao;

import com.ihrm.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jack hao
 * @createTime 2020-06-10-2:04
 */
public interface RoleDao extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {
}
