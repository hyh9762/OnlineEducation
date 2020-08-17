package com.itomelet.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itomelet.commonutils.ordervo.CourseWebVo;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.vo.PublishCourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishCourseVo getPublishCourseInfo(String courseId);

    //根据课程id查询课程基本信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
