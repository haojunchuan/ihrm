package com.ihrm.common.controller;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 父类控制器
 * @author jack hao
 * @createTime 2020-06-09-20:23
 */
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse respones;
    protected String companyId;
    protected String companyName;
//    protected Claims claims;

    @ModelAttribute//在执行控制器方法前执行该注解下的代码
    public void setReqAndRes(HttpServletRequest request,HttpServletResponse rewponse){
        this.request=request;
        this.respones=rewponse;
        this.companyId="1";
        this.companyName="江苏传智播客教育股份有限公司";

//        Claims user_claims = (Claims) request.getAttribute("user_claims");
//        this.companyName= (String) user_claims.get("companyName");
//        this.companyId= (String) user_claims.get("companyId");
//        this.claims=user_claims;
    }

    public String parseCompanyId(){
        return "1";
    }

    public String parseCompanyName(){
        return "江苏传智播客教育股份有限公司";
    }
}
