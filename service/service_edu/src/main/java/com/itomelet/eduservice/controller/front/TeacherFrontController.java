package com.itomelet.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.EduTeacher;
import com.itomelet.eduservice.service.EduCourseService;
import com.itomelet.eduservice.service.EduTeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Resource
    private EduTeacherService teacherService;

    @Resource
    private EduCourseService courseService;

    //1 分页查询讲师的方法
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);
        //返回分页所有数据
        return Result.success().data(map);
    }

    //2 讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId) {
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return Result.success().data("teacher", eduTeacher).data("courseList", courseList);
    }
}












