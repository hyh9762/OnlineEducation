package com.itomelet.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.frontvo.CourseFrontVo;
import com.itomelet.eduservice.entity.frontvo.CourseWebVo;
import com.itomelet.eduservice.entity.vo.chapter.ChapterVo;
import com.itomelet.eduservice.service.EduChapterService;
import com.itomelet.eduservice.service.EduCourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Resource
    private EduCourseService courseService;

    @Resource
    private EduChapterService chapterService;

    //1 条件查询带分页查询课程
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        //返回分页所有数据
        return Result.success().data(map);
    }

    //2 课程详情的方法
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return Result.success().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList);
    }
}












