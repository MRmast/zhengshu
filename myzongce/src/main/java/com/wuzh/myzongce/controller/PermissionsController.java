package com.wuzh.myzongce.controller;

import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.wuzh.myzongce.common.R;
import com.wuzh.myzongce.entity.Permissions;
import com.wuzh.myzongce.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther :Wuzh
 * @date :2022/12/28/17:12
 */
@RestController
@RequestMapping("/permissions")
public class PermissionsController {
    @Autowired
    private PermissionsService permissionsService;

    @PostMapping("/update")
    public R<String> update(int kind,int status){
        Permissions permissions=new Permissions();
        permissions.setId(kind);
        permissions.setStatus(status);
        boolean update = permissionsService.updateById(permissions);
        if (update){
            return R.success("修改成功");

        }else {
            R.error("修改失败");
        }
        return null;
    }
    @GetMapping("/get")
    public R<Permissions> get(){
        Permissions byId = permissionsService.getById(1);
        return R.success(byId,"查询成功");
    }
    @GetMapping("/get2")
    public R<Permissions> update2(){
        Permissions byId = permissionsService.getById(2);
        return R.success(byId,"查询成功");
    }
}
