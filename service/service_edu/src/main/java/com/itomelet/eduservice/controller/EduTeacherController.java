package com.itomelet.eduservice.controller;


import com.atguigu.commonutils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itomelet.eduservice.entity.EduTeacher;
import com.itomelet.eduservice.service.EduTeacherService;
import com.itomelet.eduservice.vo.TeacherQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
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
     */
    @GetMapping("/findAll")
    @ApiOperation("讲师列表")
    public Result findAlTeacher() {
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return Result.success().data("items", teachers);
    }


    /**
     * 删除讲师方法
     *
     * @param id 讲师id
     */
    @ApiOperation("根据id删除讲师")
    @DeleteMapping("/{id}")
    public Result removeTeacher(@ApiParam("讲师id") @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        return flag ? Result.success() : Result.error();
    }

    /**
     * 分页查询讲师方法
     */
    @ApiOperation("分页列出所有讲师")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用service方法实现分页
        eduTeacherService.page(pageTeacher);
        return Result.success().data("total", pageTeacher.getTotal()).data("rows", pageTeacher.getRecords());
    }

    /**
     * 条件查询讲师带分页
     */
    @ApiOperation("条件查询讲师带分页")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@RequestBody(required = false) TeacherQuery teacherQuery, @PathVariable long current, @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        Date begin = teacherQuery.getBegin();
        Date end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.lambda().like(EduTeacher::getName, name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.lambda().eq(EduTeacher::getLevel, level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.lambda().ge(EduTeacher::getGmtCreate, begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.lambda().le(EduTeacher::getGmtCreate, end);
        }

        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher, wrapper);
        return Result.success().data("total", pageTeacher.getTotal()).data("rows", pageTeacher.getRecords());

    }

    /**
     * 添加讲师
     */
    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        return eduTeacherService.save(eduTeacher) ? Result.success() : Result.error();
    }

    /**
     * 根据讲师id进行查询
     */
    @GetMapping("/getTeacher/{id}")
    public Result getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return Result.success().data("teacher", eduTeacher);
    }

    /**
     * 讲师修改功能
     */
    @ApiOperation("讲师信息修改")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        return eduTeacherService.updateById(eduTeacher) ? Result.success() : Result.error();
    }
}

