package com.itomelet.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.eduservice.entity.EduSubject;
import com.itomelet.eduservice.entity.excel.SubjectData;
import com.itomelet.eduservice.listener.SubjectExcelListener;
import com.itomelet.eduservice.mapper.EduSubjectMapper;
import com.itomelet.eduservice.service.EduSubjectService;
import com.itomelet.servicebase.exception.GuliException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-07-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Transactional
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "添加失败");
        }
    }
}
