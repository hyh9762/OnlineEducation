package com.itomelet.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.EduTeacher;
import com.itomelet.eduservice.service.EduCourseService;
import com.itomelet.eduservice.service.EduTeacherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eduservice/indexFront")
@CrossOrigin
public class IndexFrontController {

    @Resource
    private EduCourseService eduCourseService;
    @Resource
    private EduTeacherService eduTeacherService;

    //查询前8门热门课程，查询前4条名师
    @GetMapping("/index")
    public Result index() {
        //查询前8门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(EduCourse::getId);
        wrapper.last("limit 8");
        List<EduCourse> eduCourseList = eduCourseService.list(wrapper);
        //查询前4名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(EduCourse::getId);
        wrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(wrapperTeacher);
        return Result.success().data("eduCourseList", eduCourseList).data("eduTeacherList", eduTeacherList);
    }
}
