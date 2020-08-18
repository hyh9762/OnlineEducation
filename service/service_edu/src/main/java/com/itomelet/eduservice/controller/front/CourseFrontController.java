package com.itomelet.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itomelet.commonutils.JwtUtils;
import com.itomelet.commonutils.Result;
import com.itomelet.commonutils.ordervo.CourseWebVo;
import com.itomelet.eduservice.client.OrdersClient;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.frontvo.CourseFrontVo;
import com.itomelet.eduservice.entity.vo.chapter.ChapterVo;
import com.itomelet.eduservice.service.EduChapterService;
import com.itomelet.eduservice.service.EduCourseService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    @Resource
    private OrdersClient ordersClient;

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
    public Result getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        boolean isBuy = false;
        if (courseWebVo.getPrice().compareTo(BigDecimal.valueOf(0)) > 0) {
            //查询课程是否已经被支付过
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            if (!StringUtils.isEmpty(memberId)) {
                isBuy = ordersClient.isBuyCourse(courseId, memberId);
            }
        }


        return Result.success().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuy", isBuy);
    }


}












