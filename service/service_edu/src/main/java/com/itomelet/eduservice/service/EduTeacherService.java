package com.itomelet.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.eduservice.entity.EduTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-07-23
 */
public interface EduTeacherService extends IService<EduTeacher> {


    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
