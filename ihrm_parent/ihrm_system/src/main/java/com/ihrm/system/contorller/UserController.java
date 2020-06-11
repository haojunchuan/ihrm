package com.ihrm.system.contorller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.system.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jack hao
 * @createTime 2020-06-10-0:28
 */
@RestController
@RequestMapping("/sys")
@CrossOrigin
@Api("系统操作")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;
    /**
     * 保存用户
     *
     * @param
     * @return
     */
    @ApiOperation("添加用户")
    @PostMapping("/user")
    public Result save(@RequestBody User u) {
        u.setCompanyId(companyId);
        u.setCompanyName(companyName);
        userService.add(u);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @ApiOperation("分页查询所有")
    @GetMapping("/user/{pageNum}/{pageSize}")
    public Result findAll(@PathVariable("pageNum") Integer pageNum,
                          @PathVariable("pageSize") Integer pageSize,
                          @RequestParam Map map) {
        map.put("companyId", companyId);
        Page pager = userService.getAll(map, pageNum, pageSize);
        PageResult<User> pageResult = new PageResult<>(pager.getTotalElements(), pager.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 根据id查询部用户
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询")
    @GetMapping("/user/{id}")
    public Result findById(@PathVariable("id") String id) {
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS, user);
    }

    /**
     * 修改用户
     *
     * @param id
     * @param user
     * @return
     */
    @ApiOperation("根据id更新用户")
    @PutMapping("/user/{id}")
    public Result update(@PathVariable("id") String id, @RequestBody User user) {
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id删除用户")
    @DeleteMapping(value = "/department/{id}",name = "API-USER-DELETE")
    public Result deleteById(@PathVariable("id") String id) {
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 分配角色
     */
    @PutMapping("/user/assignRole")
    public Result assignRole(@RequestBody Map<String, Object> map) {
        //1.获取用户id
        String uid = (String) map.get("id");
        //2.获取角色列表
        List<String> list = (List<String>) map.get("roleIds");

        userService.assignRole(uid, list);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String ,Object> loginMap){
        User user = userService.findByMobile((String) loginMap.get("username"));

        if(null == user || !user.getPassword().equals(loginMap.get("password"))){//登录失败
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else{//登录成功
            //获取pai权限字符串
            StringBuilder apiStr=new StringBuilder();
            for(Role role:user.getRoles()){
                for(Permission permission:role.getPermissions()){
                    if(permission.getType().equals(PermissionConstants.PY_API)){
                        apiStr.append(permission.getCode()).append(",");
                    }
                }
            }

            Map<String,Object> info=new HashMap<>();
            info.put("companyID",user.getCompanyId());
            info.put("companyName",user.getCompanyName());
            info.put("api",apiStr.toString());
            String token=jwtUtils.createToken(user.getId(),(String) loginMap.get("username"),info);
            return new Result(ResultCode.SUCCESS,token);
        }
    }

    /**
     * 用户登录成功后，获取用户的信息
     */
    @PostMapping("profile")
    public Result profile(HttpServletRequest request) throws CommonException {
        //1.获取token
        String token = request.getHeader("Authorization");
        if(token == null){
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        token=token.replace("Bearer ","");
        Claims claims = jwtUtils.parseToken(token);

        //2.从token中获取uid
        String uid = (String) claims.getId();
        User user=userService.findById(uid);
        return new Result(ResultCode.SUCCESS,new ProfileResult(user));
    }
}
