package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jack hao
 * @createTime 2020-06-09-19:42
 */
@RestController
@CrossOrigin//解决跨域问题
@RequestMapping("/company")
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /**
     * 保存部门
     * @param d
     * @return
     */
    @PostMapping("/department")
    public Result save(@RequestBody Department d){
        String id = "1";
        d.setId(id);
        departmentService.add(d);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询该公司下的所有部门信息
     * @return
     */
    @GetMapping("/department")
    public Result findAll(){
        String comId="1";
        Company company=companyService.findById(comId);
        List<Department> all = departmentService.getAll(comId);
        DeptListResult deptListResult=new DeptListResult(company,all);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }

    /**
     * 根据部门id查询部门信息
     * @param id
     * @return
     */
    @GetMapping("/department/{id}")
    public Result findById(@PathVariable("id")String id){
        Department department=departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

    /**
     * 修改部门信息
     * @param id
     * @param d
     * @return
     */
    @PutMapping("/department/{id}")
    public Result update(@PathVariable("id") String id,@RequestBody Department d){
        d.setId(id);
        departmentService.update(d);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除部门信息
     * @param id
     * @return
     */
    @DeleteMapping("/department/{id}")
    public Result deleteById(@PathVariable("id") String id){
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
