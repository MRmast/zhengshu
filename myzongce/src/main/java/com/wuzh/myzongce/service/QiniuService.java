package com.wuzh.myzongce.service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wuzh.myzongce.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class QiniuService {

    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    // 设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "TQy9512NMJQVTotvAH4cKRp8gmeIygA3lHUmfgnH";
    String SECRET_KEY = "6kk7Oi7yuGF-2RgDaT56Ae7luQGLjDxOhyqIfo7f";
    // 要上传的空间
    String bucketname = "wuzh-space002";

    // 密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 构造一个带指定Zone对象的配置类,不同的七云牛存储区域调用不同的zone
    Configuration cfg = new Configuration(Zone.zone2());
    // ...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);

    // 测试域名，只有30天有效期
    private static String QINIU_IMAGE_DOMAIN = "http://rnj70f89c.hn-bkt.clouddn.com/";

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            // 判断是否是合法的文件后缀
            if (!FileUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            // 调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {

                log.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            log.error("七牛异常:" + e.getMessage());
            // 请求失败时打印的异常的信息

            return null;
        }
    }
}
