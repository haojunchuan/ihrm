package com.ihrm.common.controller;

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

    @ModelAttribute//在执行控制器方法前执行该注解下的代码
    public void setReqAndRes(HttpServletRequest request,HttpServletResponse rewponse){
        this.request=request;
        this.respones=rewponse;

    }

    public String parseCompanyId(){
        return "1";
    }

    public String parseCompanyName(){
        return "江苏传智播客教育股份有限公司";
    }
}
