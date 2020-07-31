package com.itomelet.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itomelet.eduservice.entity.EduSubject;
import com.itomelet.eduservice.entity.excel.SubjectData;
import com.itomelet.eduservice.service.EduSubjectService;
import com.itomelet.servicebase.exception.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //SubjectExcelListener不能交给Spring管理，需要自己new，不能注入其他对象
    private EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 读取excel内容，一行一行读取
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }
        //一行一行读取，每次读取都有两个值，一级分类和二级分类
        EduSubject oneSubject = existOneSubject(subjectData.getOneSubjectName());
        if (oneSubject == null) {
            //进行添加
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName()); //一级分类名称
            subjectService.save(oneSubject);
        }
        EduSubject twoSubject = existTwoSubject(subjectData.getOneSubjectName(), oneSubject.getId());
        if (twoSubject == null) {
            //进行添加
            twoSubject = new EduSubject();
            twoSubject.setParentId(oneSubject.getId());
            twoSubject.setTitle(subjectData.getTwoSubjectName()); //二级分类名称
            subjectService.save(twoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduSubject::getTitle, name).eq(EduSubject::getParentId, "0");
        return subjectService.getOne(wrapper);
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduSubject::getTitle, name).eq(EduSubject::getParentId, pid);
        return subjectService.getOne(wrapper);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
