package com.itomelet.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.eduservice.entity.EduSubject;
import com.itomelet.eduservice.entity.excel.SubjectData;
import com.itomelet.eduservice.entity.vo.SubjectVo;
import com.itomelet.eduservice.listener.SubjectExcelListener;
import com.itomelet.eduservice.mapper.EduSubjectMapper;
import com.itomelet.eduservice.service.EduSubjectService;
import com.itomelet.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

    EduSubjectMapper eduSubjectMapper;

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

    /**
     * 课程分类列表，树形结构
     */
    @Override
    public List<SubjectVo> getAllSubject() {
        return getChildSubject("0");
    }

    /**
     * 查询一个课程分类的直接子分类
     *
     * @param id 课程分类id
     * @return 子分类集合
     */
    private List<SubjectVo> getChildSubject(String id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduSubject::getParentId, id);
        List<EduSubject> childEduSubjects = baseMapper.selectList(wrapper);
        if (childEduSubjects == null) {
            return null;
        }
        List<SubjectVo> subjects = new ArrayList<>();
        for (EduSubject childEduSubject : childEduSubjects) {
            SubjectVo childSubject = new SubjectVo();
            BeanUtils.copyProperties(childEduSubject, childSubject);
            childSubject.setChildren(getChildSubject(childEduSubject.getId()));
            subjects.add(childSubject);
        }
        return subjects;
    }
}
