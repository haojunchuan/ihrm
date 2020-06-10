package com.ihrm.system.contorller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 保存用户
     * @param
     * @return
     */
    @ApiOperation("添加用户")
    @PostMapping("/user")
    public Result save(@RequestBody User u){
        u.setCompanyId(companyId);
        u.setCompanyName(companyName);
        userService.add(u);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询所有用户
     * @return
     */
    @ApiOperation("分页查询所有")
    @GetMapping("/user/{pageNum}/{pageSize}")
    public Result findAll(@PathVariable("pageNum")Integer pageNum,
                          @PathVariable("pageSize")Integer pageSize,
                          @RequestParam Map map){
        map.put("companyId",companyId);
        Page pager = userService.getAll(map, pageNum, pageSize);
        PageResult<User> pageResult=new PageResult<>(pager.getTotalElements(),pager.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 根据id查询部用户
     * @param id
     * @return
     */
    @ApiOperation("根据id查询")
    @GetMapping("/user/{id}")
    public Result findById(@PathVariable("id")String id){
        User user=userService.findById(id);
        return new Result(ResultCode.SUCCESS,user);
    }

    /**
     * 修改用户
     * @param id
     * @param user
     * @return
     */
    @ApiOperation("根据id更新用户")
    @PutMapping("/user/{id}")
    public Result update(@PathVariable("id") String id, @RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ApiOperation("根据id删除用户")
    @DeleteMapping("/department/{id}")
    public Result deleteById(@PathVariable("id") String id){
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     *分配角色
     */
    @PutMapping("/user/assignRole")
    public Result assignRole(@RequestBody Map<String ,Object> map){
        //1.获取用户id
        String uid= (String) map.get("id");
        //2.获取角色列表
        List<String> list= (List<String>) map.get("roleIds");

        userService.assignRole(uid,list);
        return new Result(ResultCode.SUCCESS);
    }
}
