package com.wuzh.myzongce.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @auther :Wuzh
 * @date :2022/12/25/17:12
 */
@Data
public class Books {
    private String id;
    private int userid;

    private String bookname;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gettime;

    private String bookscore;

    private String location;

    private String level;

    private int status;
    //0表示未审核,1表示审核
}
