package com.wuzh.myzongce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuzh.myzongce.mapper.UserMapper;
import com.wuzh.myzongce.service.UserService;
import org.springframework.stereotype.Service;
import com.wuzh.myzongce.entity.Users;

/**
 * @auther :Wuzh
 * @date :2022/12/23/16:12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,Users> implements UserService {
}
