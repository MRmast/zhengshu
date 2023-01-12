package com.wuzh.myzongce.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wuzh.myzongce.common.R;
import com.wuzh.myzongce.entity.Users;
import com.wuzh.myzongce.service.UserService;
import com.wuzh.myzongce.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther :Wuzh
 * @date :2022/12/23/16:12
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<Users> login(HttpServletRequest request, @RequestBody Users users){
        String password= users.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Users> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getId,users.getUsername());
        Users users1=userService.getOne(queryWrapper);
        if (users1==null){
            return R.error("登录失败，未查询到用户");
        }

        if (!users1.getPassword().equals(password)){
            System.out.println("1^^^:"+users1.getPassword());
            System.out.println("2^^^:"+password);

            return R.error("登录失败，密码错误");
        }
        if (users1.getStatus()==0){
            return R.error("登录失败，账号被禁用");
        }
        request.getSession().setAttribute("user",users1.getId());

        String token= JWTUtils.createToken((long) users1.getId());

        return R.success(users1,"登陆成功");
    }
    @PostMapping("/logout")
    public R<Users> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return R.success("退出成功");

    }
    @GetMapping("/infos{userid}")
    public R<Users> find(int userid){
        System.out.println("用户名"+userid);
        Users byId = userService.getById(userid);
        return R.success(byId,"查询成功");
    }
    @PostMapping("/update")
    public R<String> update(@RequestBody Users users){
        System.out.println(users);
        boolean b = userService.updateById(users);
        if (b){
            return R.success("修改成功");
        }else {
            return R.error("修改失败");
        }
    }
}
