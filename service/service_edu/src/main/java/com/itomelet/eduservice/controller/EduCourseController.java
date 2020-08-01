package com.itomelet.eduservice.controller;


import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.entity.vo.CourseInfoVo;
import com.itomelet.eduservice.service.EduCourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Resource
    private EduCourseService eduCourseService;

    /**
     * 添加课程基本信息
     */
    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.success();
    }
}

