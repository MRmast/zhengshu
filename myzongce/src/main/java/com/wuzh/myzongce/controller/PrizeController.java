package com.wuzh.myzongce.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzh.myzongce.common.R;
import com.wuzh.myzongce.entity.Prize;
import com.wuzh.myzongce.service.PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther :Wuzh
 * @date :2022/12/28/19:12
 */
@RestController
@RequestMapping("/prize")
@Slf4j
public class PrizeController {
    @Autowired
    private PrizeService prizeService;
    @GetMapping("/getall")
    public R<Page> get(int page,int pagesize,int userid){
        Page pageinfo =new Page(page,pagesize);
        LambdaQueryWrapper<Prize> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.like(Prize::getUserid,userid);
        queryWrapper.orderByDesc(Prize::getCreatime);
        prizeService.page(pageinfo,queryWrapper);
        return R.success(pageinfo,"查询成功");
    }
    @GetMapping("/getall2")
    public R<Page> get2(int page,int pagesize){
        Page pageinfo =new Page(page,pagesize);
        LambdaQueryWrapper<Prize> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.orderByDesc(Prize::getCreatime);
        prizeService.page(pageinfo,queryWrapper);
        return R.success(pageinfo,"查询成功");
    }
}
