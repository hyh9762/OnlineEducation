package com.itomelet.statistics.controller;


import com.itomelet.commonutils.Result;
import com.itomelet.statistics.service.DailyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-18
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {

    @Resource
    private DailyService dailyService;

    //统计一天注册人数
    @PostMapping("/registerCount/{day}")
    public Result registerCount(@PathVariable String day) {
        dailyService.registerCount(day);
        return Result.success();
    }
}

