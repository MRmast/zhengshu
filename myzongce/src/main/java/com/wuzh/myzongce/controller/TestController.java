package com.wuzh.myzongce.controller;

import com.wuzh.myzongce.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class TestController {
    
    @Autowired
    private QiniuService qiniuService;
    @CrossOrigin
    @RequestMapping(value = "/testUpload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        
        if(file.isEmpty()) {
            return "error";
        }
        
        try {
            String fileUrl=qiniuService.saveImage(file);
            return "success, imageUrl = " + fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
