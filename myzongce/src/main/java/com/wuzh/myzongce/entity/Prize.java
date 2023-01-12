package com.wuzh.myzongce.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @auther :Wuzh
 * @date :2022/12/28/18:12
 */
@Data
public class Prize {
    private int id;

    private String username;

    private String userid;

    private String prize;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creatime;
}
