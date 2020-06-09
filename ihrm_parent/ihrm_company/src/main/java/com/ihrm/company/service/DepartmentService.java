package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author jack hao
 * @createTime 2020-06-09-18:35
 */
@Service
public class DepartmentService extends BaseService<Department> {
    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 1.保存部门
     */
    public void add(@RequestBody Department department){
        String id=idWorker.nextId()+"";
        department.setId(id);
        departmentDao.save(department);
    }
    /**
     * 2.更新部门
     */
    public void update(@RequestBody Department department){
        Department department1 = departmentDao.findById(department.getId()).get();
        department1.setCode(department.getCode());
        department1.setCompanyId(department.getCompanyId());
        department1.setManager(department.getManager());
        departmentDao.save(department1);
    }
    /**
     * 3.根据id查询部门
     */
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }
    /**
     * 4.查询全部部门
     */
    public List<Department> getAll(String id){
        //构造查询条件(后面抽取到公共的service)
//        Specification<Department> spec=new Specification<Department>() {
//            @Override
//            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
//                return cb.equal(root.get("companyId").as(String.class),id);
//            }
//        };
        return departmentDao.findAll(getSpec(id));
    }
    /**
     * 5.根据id删除部门
     */
    public void deleteById(String id){
        departmentDao.deleteById(id);
    }
}
