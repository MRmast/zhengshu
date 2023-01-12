package com.wuzh.myzongce.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzh.myzongce.common.R;
import com.wuzh.myzongce.entity.Books;
import com.wuzh.myzongce.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @auther :Wuzh
 * @date :2022/12/24/23:12
 */
@Slf4j
@RestController
@RequestMapping("/add")
public class BookController {
    private String path="F:\\寒假\\20221223\\vue\\zongce\\src\\img\\";
    @Autowired
    private BookService bookService;
    @CrossOrigin
    @PostMapping("/photo")
    public R<String> addscore(MultipartFile file){
        log.info(file.toString());
        String originalFilename= file.getOriginalFilename();
        String suffix =originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName= UUID.randomUUID().toString()+suffix;
        File dir=new File(path);
        if (!dir.exists()){
            dir.mkdirs();
            log.info("创建文件夹");
        }
        try {
            file.transferTo(new File(path+fileName));
            System.out.println(path+fileName);
            log.info("创建文件");
        }catch (IOException io){

            io.printStackTrace();
        }
        return R.success(fileName);
    }
    @GetMapping("/downphoto")
    public void download(String name, HttpServletResponse response){
        try{
            FileInputStream fileInputStream=new FileInputStream(new File(path+name));
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes=new byte[1024];
            int len=0;
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @PostMapping("/book")
    public R<Books> addbook(@RequestBody Books books){
        System.out.println(books.toString());
        System.out.println(books.getGettime());
        String random =String.valueOf((int)(Math.random()*100000));
        System.out.println("随机生成的数字");
        System.out.println(Math.random());
        System.out.println(random);
        books.setId(random);
        bookService.save(books);
        return R.success("添加成功");
    }
    @GetMapping("/info")
    public R<Page> page(int page,int pagesize,int userid){
        Page pageinfo =new Page(page,pagesize);
        LambdaQueryWrapper<Books> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.like(Books::getUserid,userid);
        queryWrapper.orderByDesc(Books::getGettime);
        bookService.page(pageinfo,queryWrapper);
        return R.success(pageinfo,"查询成功");
    }
    @GetMapping("/infoall")
    public R<Page> pageall(int page,int pagesize){
        Page pageinfo =new Page(page,pagesize);
        LambdaQueryWrapper<Books> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.orderByDesc(Books::getGettime);
        queryWrapper.orderByDesc(Books::getStatus);
        bookService.page(pageinfo,queryWrapper);
        return R.success(pageinfo,"查询成功");
    }
    @PostMapping("/updatestatus")
    public R<String> updatestatus(int bookid,int status){
        Books byId = bookService.getById(bookid);
        byId.setStatus(status);
        boolean update = bookService.updateById(byId);
        System.out.println("id?"+bookService.getById(bookid));
        if (update){
            return R.success("修改成功");

        }else {
            R.error("修改失败");
        }
        return null;
    }

}
