package com.itomelet.eduservice.controller;


import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.client.VodClient;
import com.itomelet.eduservice.entity.EduVideo;
import com.itomelet.eduservice.service.EduVideoService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

    @Resource
    EduVideoService eduVideoService;
    @Resource
    VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return Result.success();
    }

    //根据小节id查询
    @GetMapping("/getVideoInfo/{videoId}")
    public Result getVideoInfo(@PathVariable String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return Result.success().data("Video", eduVideo);
    }

    //修改小节
    @PostMapping("/updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return Result.success();
    }

    //删除小节
    @DeleteMapping("/{id}")
    public Result deleteVideo(@PathVariable String id) {
        //删除阿里云上的视频
        //根据小节id获取视频id
        String videoSourceId = eduVideoService.getById(id).getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeAliyunVideo(videoSourceId);
        }
        //删除小节
        boolean flag = eduVideoService.removeById(id);
        if (flag) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

}

