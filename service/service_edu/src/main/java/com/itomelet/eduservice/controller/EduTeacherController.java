package com.itomelet.eduservice.controller;


import com.itomelet.eduservice.entity.EduTeacher;
import com.itomelet.eduservice.service.EduTeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //把service注入
    @Resource
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有方法
     *
     * @return teacher集合
     */
    @GetMapping("/findAll")
    public List<EduTeacher> findAlTeacher() {
        return eduTeacherService.list(null);
    }


    @DeleteMapping("/{id}")
    public boolean removeTeacher(@PathVariable String id) {
        return eduTeacherService.removeById(id);
    }
}

