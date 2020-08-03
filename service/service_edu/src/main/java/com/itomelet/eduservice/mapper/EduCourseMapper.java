package com.itomelet.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
