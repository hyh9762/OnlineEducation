package com.itomelet.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.frontvo.CourseFrontVo;
import com.itomelet.eduservice.entity.frontvo.CourseWebVo;
import com.itomelet.eduservice.entity.vo.CourseInfoVo;
import com.itomelet.eduservice.entity.vo.PublishCourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    PublishCourseVo getPublishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
