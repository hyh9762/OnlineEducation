package com.itomelet.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itomelet.commonutils.Result;
import com.itomelet.commonutils.ordervo.CourseWebVo;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.vo.CourseInfoVo;
import com.itomelet.eduservice.entity.vo.CourseQuery;
import com.itomelet.eduservice.entity.vo.PublishCourseVo;
import com.itomelet.eduservice.service.EduCourseService;
import org.springframework.util.StringUtils;
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
public class EduCourseController {

    @Resource
    private EduCourseService eduCourseService;

    //课程列表 条件查询带分页
    @PostMapping("/pageCourse/{current}/{limit}")
    public Result pageTeacherCondition(CourseQuery courseQuery, @PathVariable long current, @PathVariable long limit) {
        //创建page对象
        Page<EduCourse> pageEduCourse = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.lambda().like(EduCourse::getTitle, title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.lambda().eq(EduCourse::getStatus, status);
        }

        //调用方法实现条件查询分页
        eduCourseService.page(pageEduCourse, wrapper);
        return Result.success().data("total", pageEduCourse.getTotal()).data("rows", pageEduCourse.getRecords());

    }

    /**
     * 添加课程基本信息
     */
    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回课程id
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.success().data("courseId", id);
    }

    //根据课程查询课程基本信息
    @GetMapping("/getCourseBaseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return Result.success().data("courseInfoVo", courseInfoVo);
    }

    //更新课程基本信息
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.success();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id) {
        PublishCourseVo publishCourseVo = eduCourseService.getPublishCourseInfo(id);
        return Result.success().data("publishCourse", publishCourseVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("/publishCourse/{id}")
    public Result publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程最终发布状态
        eduCourseService.updateById(eduCourse);
        return Result.success();
    }

    //删除课程
    @DeleteMapping("/{courseId}")
    public Result removeCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return Result.success();
    }

    //根据课程id查询课程信息(模块间传递)
    @GetMapping("getCourseOrderInfo/{id}")
    public CourseWebVo getCourseInfoVo(@PathVariable String id) {
        return eduCourseService.getBaseCourseInfo(id);
    }
}

