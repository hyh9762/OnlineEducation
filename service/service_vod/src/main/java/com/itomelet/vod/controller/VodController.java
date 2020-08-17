package com.itomelet.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.itomelet.commonutils.Result;
import com.itomelet.servicebase.exception.GuliException;
import com.itomelet.vod.service.VodService;
import com.itomelet.vod.utils.ConstantPropertiesUtils;
import com.itomelet.vod.utils.InitVodClient;
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

    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.success().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
