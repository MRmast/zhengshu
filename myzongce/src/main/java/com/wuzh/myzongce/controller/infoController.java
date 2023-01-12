package com.wuzh.myzongce.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzh.myzongce.common.R;
import com.wuzh.myzongce.entity.TotalScore;
import com.wuzh.myzongce.service.TotalScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther :Wuzh
 * @date :2022/12/27/23:12
 */
@RestController
@RequestMapping("/people")
@Slf4j
public class infoController {
    @Autowired
    private TotalScoreService totalScoreService;
    @GetMapping("/getall")
    public R<Page> getall(int page,int pagesize){
        Page pageinfo =new Page(page,pagesize);
        LambdaQueryWrapper<TotalScore> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.orderByDesc(TotalScore::getR);
        totalScoreService.page(pageinfo,queryWrapper);
        return R.success(pageinfo,"查询成功");

    }
}
