package com.wuzh.myzongce.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

/**
 * @auther :Wuzh
 * @date :2022/12/23/15:12
 */
@Data
public class Users {
    private int id;

    private String class1;

    private String username;

    private String password;

    private int level;

    private int status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private Date createtime;
}
