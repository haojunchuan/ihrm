package com.ihrm.system.contorller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jack hao
 * @createTime 2020-06-10-2:20
 */
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping("/role")
    public Result add(@RequestBody Role role){
        role.setCompanyId(companyId);
        roleService.save(role);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id更新角色
     * @param id
     * @param role
     * @return
     */
    @PutMapping("/role/{id}")
    public Result update(@PathVariable("id")String id,@RequestBody Role role){
        role.setId(id);
        roleService.update(role);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除角色
     * @param rid
     * @return
     */
    @DeleteMapping("/role/{rid}")
    public Result delete(@PathVariable("rid")String rid){
        roleService.delete(rid);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据ID查询角色
     * @param rid
     * @return
     */
    @GetMapping("/role/{rid}")
    public Result findById(@PathVariable("rid")String rid){
        Role role=roleService.findById(rid);
        return new Result(ResultCode.SUCCESS,role);
    }

    /**
     * 查询出所有数据
     * @return
     */
    @GetMapping("/role")
    public Result findAll(){
        List<Role> all = roleService.findAll(companyId);
        System.out.println(all.size());
        return new Result(ResultCode.SUCCESS,all);
    }

    /**
     * 分页查询出所有角色
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/role/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        Page<Role> searchPage = roleService.findByPage(companyId, pageNum, pageSize);
        PageResult<Role> pr = new PageResult(searchPage.getTotalElements(),searchPage.getContent());
        return new Result(ResultCode.SUCCESS,pr);
    }


    /**
     *分配权限
     */
    @PutMapping("/role/assignRole")
    public Result assignRole(@RequestBody Map<String ,Object> map){
        //1.获取权限id
        String uid= (String) map.get("id");
        //2.获取权限列表
        List<String> list= (List<String>) map.get("permIds");

        roleService.assignPerm(uid,list);
        return new Result(ResultCode.SUCCESS);
    }
}
