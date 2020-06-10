package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jack hao
 * @createTime 2020-06-10-2:05
 */
@Service
public class RoleService extends BaseService<Role> {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存角色
     * @param role
     */
    public void save(Role role){
        role.setId(idWorker.nextId()+"");
        roleDao.save(role);
    }

    /**
     * 更新角色
     * @param role
     */
    public void update(Role role){
        Role target=roleDao.findById(role.getId()).get();
        target.setDescription(role.getDescription());
        target.setName(role.getName());
        roleDao.save(role);
    }

    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    public Role findById(String id){
        return roleDao.findById(id).get();
    }

    /**
     * 根据公司id查询角色信息
     * @param companyId
     * @return
     */
    public List<Role> findAll(String companyId){
        return roleDao.findAll(getSpec(companyId));
    }

    /**
     * 根据角色id删除角色
     * @param rid
     */
    public void delete(String rid){
        roleDao.deleteById(rid);
    }

    public Page<Role> findByPage(String companyId,Integer pageNum,Integer pageSize){
        return roleDao.findAll(getSpec(companyId),PageRequest.of(pageNum,pageSize));
    }

    /**
     * 分配权限
     * @param rid
     * @param list
     */
    public void assignPerm(String rid, List<String> list) {
        Role role = roleDao.findById(rid).get();
        Set<Permission> pers=new HashSet<>();
        for(String permId : list){
            Permission permission = permissionDao.findById(permId).get();

            //需要根据父id和类型查询API权限列表
            List<Permission> permApi = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getPid());
            pers.addAll(permApi);//自动赋予改角色API权限

            pers.add(permission);//当前菜单或按钮权限
        }

        //设置角色和权限的关系
        role.setPermissions(pers);

        //更新角色
        roleDao.save(role);
    }
}
