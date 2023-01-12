package com.wuzh.myzongce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuzh.myzongce.entity.Books;
import com.wuzh.myzongce.mapper.BookMapper;
import com.wuzh.myzongce.service.BookService;
import org.springframework.stereotype.Service;

/**
 * @auther :Wuzh
 * @date :2022/12/25/18:12
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Books> implements BookService {
}
