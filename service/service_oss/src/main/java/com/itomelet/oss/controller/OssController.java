package com.itomelet.oss.controller;

import com.itomelet.commonutils.Result;
import com.itomelet.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public Result uploadOssFile(MultipartFile file) {
        //获取上传文件 MulitipartFile
        String url = ossService.uploadFileAvatar(file);
        return Result.success().data("url", url);
    }

}
