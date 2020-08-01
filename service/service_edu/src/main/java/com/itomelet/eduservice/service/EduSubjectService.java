package com.itomelet.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.eduservice.entity.EduSubject;
import com.itomelet.eduservice.entity.subject.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-07-31
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<Subject> getAllSubject();
}
