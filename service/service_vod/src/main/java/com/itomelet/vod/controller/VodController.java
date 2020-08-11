package com.itomelet.vod.controller;

import com.itomelet.commonutils.Result;
import com.itomelet.vod.service.VodService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Resource
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadAliyunVideo")
    public Result uploadAliyunVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return Result.success().data("videoId", videoId);
    }
}
