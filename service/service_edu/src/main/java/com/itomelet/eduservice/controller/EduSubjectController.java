package com.itomelet.eduservice.controller;


import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.entity.vo.SubjectVo;
import com.itomelet.eduservice.service.EduSubjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Resource
    private EduSubjectService subjectService;

    /**
     * 添加课程分类，获取上传的文件，把文件内容读取出来
     */
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return Result.success();
    }


    /**
     * 查询课程分类，返回树形结构
     *
     * @return 一级课程分类集合，每个分类包含各自的子分类集合
     */
    @GetMapping("/getAllSubject")
    public Result getAllSubject() {
        List<SubjectVo> subjects = subjectService.getAllSubject();
        return Result.success().data("subjects", subjects);
    }
}

