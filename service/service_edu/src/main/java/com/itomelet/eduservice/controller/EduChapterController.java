package com.itomelet.eduservice.controller;


import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.entity.vo.chapter.ChapterVo;
import com.itomelet.eduservice.service.EduChapterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Resource
    private EduChapterService chapterService;

    //课程大纲列表
    @GetMapping("/getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return Result.success().data("allChapterVideo", list);
    }

}

