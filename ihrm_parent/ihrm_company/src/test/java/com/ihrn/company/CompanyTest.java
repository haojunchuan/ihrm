package com.ihrn.company;

import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author jack hao
 * @createTime 2020-06-09-5:01
 */
@SpringBootTest(classes = com.ihrm.company.CompanyApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyTest {
    @Autowired
    CompanyDao companyDao;

    @Test
    public void Test(){
        Company c = companyDao.findById("1").get();
        System.out.println(c.toString());
    }
}
