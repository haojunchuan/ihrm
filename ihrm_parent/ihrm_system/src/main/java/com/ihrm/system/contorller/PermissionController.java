package com.ihrm.system.contorller;

import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author jack hao
 * @createTime 2020-06-10-3:48
 */
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 保存
     */
    @PostMapping("/permission")
    public Result save(@RequestBody Map<String, Object> map) throws Exception {
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }


    /**
     * 更新
     */
    @PostMapping("/permission/{id}")
    public Result update(@RequestBody Map<String, Object> map, @PathVariable("id") String id) throws Exception {
        map.put("id", id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询所有
     */
    @GetMapping("/permission")
    public Result findAll(@RequestBody Map<String,Object> map) {
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS, list);
    }

    /**
     * 根据id查询
     */
    @GetMapping("/permission/{id}")
    public Result findById(@PathVariable("id") String id) throws CommonException {
        System.out.println(id);
        Map map=permissionService.findById(id);
//        Permission map = permissionDao.findById(id).get();
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/permission/{id}")
    public Result deleteById(@PathVariable("id")String id) throws CommonException {
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
