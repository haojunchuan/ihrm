package com.ihrm.company.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jack hao
 * @createTime 2020-06-09-5:23
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存企业
     */
    public void add(Company c) {
        //基本属性设置
        String id = idWorker.nextId() + "";
        c.setId(id);
        //设置一些默认状态
        c.setAuditState("0"); // 默认未被审核
        c.setState(0);//默认未激活

        companyDao.save(c);
    }
    /**
     * 更新企业
     */
    public void update(Company c){
        Company company = companyDao.findById(c.getId()).get();
        company.setCompanyAddress(c.getCompanyAddress());
        company.setState(c.getState());
        companyDao.save(c);
    }
    /**
     * 删除企业
     */
    public void delete(String id){
        companyDao.deleteById(id);
    }
    /**
     * 根据id查询企业
     */
    public Company findById(String id){
        return companyDao.findById(id).get();
    }

    /**
     * 查询企业列表
     */
    public List<Company> findAll(){
        return companyDao.findAll();
    }
}
