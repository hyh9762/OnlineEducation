package com.itomelet.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.EduDescription;
import com.itomelet.eduservice.entity.vo.CourseInfoVo;
import com.itomelet.eduservice.mapper.EduCourseMapper;
import com.itomelet.eduservice.service.EduCourseService;
import com.itomelet.eduservice.service.EduDescriptionService;
import com.itomelet.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduDescriptionService descriptionService;

    /**
     * 添加课程信息基本方法
     */
    @Override
    @Transactional

    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表中添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        //2.向课程简介表中添加课程简介
        EduDescription eduDescription = new EduDescription();
        eduDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        eduDescription.setId(eduCourse.getId());
        descriptionService.save(eduDescription);

    }
}
