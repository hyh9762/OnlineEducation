package com.itomelet.eduservice.controller;


import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.entity.EduVideo;
import com.itomelet.eduservice.service.EduVideoService;
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
    //TODO:删除小节的时候删除里面的课程
    @DeleteMapping("/{videoId}")
    public Result deleteVideo(@PathVariable String videoId) {
        boolean flag = eduVideoService.removeById(videoId);
        if (flag) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

}

