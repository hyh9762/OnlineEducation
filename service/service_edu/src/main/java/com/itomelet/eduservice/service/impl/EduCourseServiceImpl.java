package com.itomelet.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.eduservice.entity.EduCourse;
import com.itomelet.eduservice.entity.EduDescription;
import com.itomelet.eduservice.entity.frontvo.CourseFrontVo;
import com.itomelet.eduservice.entity.frontvo.CourseWebVo;
import com.itomelet.eduservice.entity.vo.CourseInfoVo;
import com.itomelet.eduservice.entity.vo.PublishCourseVo;
import com.itomelet.eduservice.mapper.EduCourseMapper;
import com.itomelet.eduservice.service.EduChapterService;
import com.itomelet.eduservice.service.EduCourseService;
import com.itomelet.eduservice.service.EduDescriptionService;
import com.itomelet.eduservice.service.EduVideoService;
import com.itomelet.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private EduDescriptionService eduDescriptionService;
    @Resource
    private EduVideoService eduVideoService;
    @Resource
    private EduChapterService eduChapterService;

    /**
     * 添加课程信息基本方法
     */
    @Override
    @Transactional

    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
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
        eduDescriptionService.save(eduDescription);
        return eduCourse.getId();

    }

    /**
     * 根据课程id回显课程基本信息
     *
     * @param courseId 课程id
     * @return 课程基本信息
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        //2.查询描述表
        EduDescription eduDescription = eduDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduDescription.getDescription());

        return courseInfoVo;
    }

    @Transactional
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        //修改描述表
        EduDescription eduDescription = new EduDescription();
        eduDescription.setId(courseInfoVo.getId());
        eduDescription.setDescription(courseInfoVo.getDescription());
        boolean flag = eduDescriptionService.updateById(eduDescription);
        if (!flag) {
            throw new GuliException(20001, "修改描述信息失败");
        }
    }

    @Override
    public PublishCourseVo getPublishCourseInfo(String id) {
        //调用mapper
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    @Transactional
    public void removeCourse(String courseId) {
        //1.根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //2.根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //3.删除描述
        eduDescriptionService.removeById(courseId);
        //4.删除章节
        boolean b = this.removeById(courseId);
        if (!b) {
            throw new GuliException(20001, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.lambda().eq(EduCourse::getSubjectParentId, courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse, wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;

    }

    //根据课程id，编写sql语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);

    }
}
