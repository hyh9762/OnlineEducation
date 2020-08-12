package com.itomelet.vod.controller;

import com.itomelet.commonutils.Result;
import com.itomelet.vod.service.VodService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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

    //根据视频id删除阿里云视频
    @DeleteMapping("/removeAliyunVideo/{id}")
    public Result removeAliyunVideo(@PathVariable String id) {
        vodService.deleteVideo(id);
        return Result.success();
    }

    //删除多个阿里云视频的方法
    @DeleteMapping("delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoList) {
        vodService.removeVideoList(videoList);
        return Result.success();
    }
}
