package com.ihrm.system.service;


import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ihrm.common.utils.PermissionConstants;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author jack hao
 * @createTime 2020-06-10-3:55
 */
@Service
@Transactional
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存权限
     * @param map
     */
    public void save(Map<String, Object> map) throws Exception {
        String id=idWorker.nextId()+"";
        //1.通过map构造Permission对象
        Permission permission= BeanMapUtils.mapToBean(map,Permission.class);
        permission.setId(id);
        //2.根据类型构造不同的资源对象（菜单、按钮、API）并进行保存
        Integer type=permission.getType();
        switch(type){
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint=BeanMapUtils.mapToBean(map,PermissionPoint.class);
                permissionPoint.setId(id);
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi=BeanMapUtils.mapToBean(map,PermissionApi.class);
                permissionApi.setId(id);
                permissionApiDao.save(permissionApi);
                break;
            case PermissionConstants.PY_MENU:
                PermissionMenu permissionMenu=BeanMapUtils.mapToBean(map,PermissionMenu.class);
                permissionMenu.setId(id);
                permissionMenuDao.save(permissionMenu);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }

        permissionDao.save(permission);

    }


    /**
     * 更新权限
     * @param map
     */
    public void update(Map<String, Object> map) throws Exception {
        Permission permission= BeanMapUtils.mapToBean(map,Permission.class);
        //1.根据传递的权限ID查询权限
        Permission targetPerm = permissionDao.findById(permission.getId()).get();
        targetPerm.setName(permission.getName());
        targetPerm.setDescription(permission.getDescription());
        targetPerm.setEnVisible(permission.getEnVisible());

        Integer type=permission.getType();
        //2.根据类型构造不同的资源
        //3.查询不同的资源设置修改属性
        switch(type){
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint=BeanMapUtils.mapToBean(map,PermissionPoint.class);
                permissionPoint.setId(permission.getId());
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi=BeanMapUtils.mapToBean(map,PermissionApi.class);
                permissionApi.setId(permission.getId());
                permissionApiDao.save(permissionApi);
                break;
            case PermissionConstants.PY_MENU:
                PermissionMenu permissionMenu=BeanMapUtils.mapToBean(map,PermissionMenu.class);
                permissionMenu.setId(permission.getId());
                permissionMenuDao.save(permissionMenu);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }

        //4.更新权限,更改资源
        permissionDao.save(permission);
    }


    /**
     *  查询所有
     * @param map
     * @return
     */
    public List<Permission> findAll(Map map) {
        //查询条件
        Specification<Permission> spec = new Specification<Permission>() {

            /**
             * 动态拼接查询条件
             * @param root
             * @param query
             * @param cb
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                //根据父id查询
                if (!StringUtils.isEmpty(map.get("pid"))){
                    list.add(cb.equal(root.get("pid").as(String.class) , (String)map.get("pid")));
                }

                //根据enVisible查询
                if (!StringUtils.isEmpty(map.get("enVisible"))){
                    list.add(cb.equal(root.get("enVisible").as(String.class) , map.get("enVisible")));
                }

                //根据类型type进行查询
                if (!StringUtils.isEmpty(map.get("type"))){
                    String type = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = cb.in(root.get("type"));

                    if ("0".equals(type)){
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(type));
                    }
                    list.add(in);
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };

        return permissionDao.findAll(spec);
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Map findById(String id) throws CommonException {
        Permission permission = permissionDao.findById(id).get();
        Integer type = permission.getType();
        Object resource=new Object();
        switch(type){
            case PermissionConstants.PY_API:
                resource=permissionApiDao.findById(id).get();
                break;
            case PermissionConstants.PY_MENU:
                resource=permissionMenuDao.findById(id).get();
                break;
            case PermissionConstants.PY_POINT:
                resource=permissionPointDao.findById(id).get();
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //3.构造map集合

        Map<String, Object> resMap = BeanMapUtils.beanToMap(resource);
        Map<String, Object> permMap = BeanMapUtils.beanToMap(permission);
        resMap.putAll(permMap);
        return resMap;
    }

    public void deleteById(String id) throws CommonException {
        //1.查询权限
        Permission perm=permissionDao.findById(id).get();
        Integer type = perm.getType();
        //2.根据权限查询资源

        Object resource=new Object();
        switch(type){
            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(id);
                break;
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        permissionDao.deleteById(id);
    }
}
