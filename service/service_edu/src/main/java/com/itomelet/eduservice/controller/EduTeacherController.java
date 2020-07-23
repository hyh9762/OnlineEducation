package com.itomelet.eduservice.controller;


import com.itomelet.eduservice.entity.EduTeacher;
import com.itomelet.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = {"讲师管理"})
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有方法
     *
     * @return teacher集合
     */
    @GetMapping("/findAll")
    @ApiOperation("讲师列表")
    public List<EduTeacher> findAlTeacher() {
        return eduTeacherService.list(null);
    }


    @ApiOperation("根据id删除讲师")
    @DeleteMapping("/{id}")
    public boolean removeTeacher(@ApiParam("讲师id") @PathVariable String id) {
        return eduTeacherService.removeById(id);
    }
}

