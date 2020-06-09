package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jack hao
 * @createTime 2020-06-09-5:48
 */
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    /**
     * 保存企业
     */
    @PostMapping("/company")
    public Result save(@RequestBody Company c){
        companyService.add(c);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id更新
     */
    @PutMapping("/company/{id}")
    public Result update(@PathVariable("id") String id,@RequestBody Company c){
        c.setId(id);
        companyService.update(c);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/company/{id}")
    public Result delete(@PathVariable("id") String id){
        companyService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询
     */
    @GetMapping("/company/{id}")
    public Result findById(@PathVariable("id") String id){
        Company company = companyService.findById(id);
        Result r=new Result(ResultCode.SUCCESS);
        r.setData(company);
        return r;
    }

    /**
     * 查询所有的company数据
     */
    @GetMapping("/company")
    public Result findAll(){
        List<Company> all = companyService.findAll();
        Result r=new Result(ResultCode.SUCCESS);
        r.setData(all);
        return r;
    }
}
