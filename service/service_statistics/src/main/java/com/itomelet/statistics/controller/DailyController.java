package com.itomelet.statistics.controller;


import com.itomelet.commonutils.Result;
import com.itomelet.statistics.service.DailyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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

    //图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type, @PathVariable String begin,
                           @PathVariable String end) {
        Map<String, Object> map = dailyService.getShowData(type, begin, end);
        return Result.success().data(map);
    }
}

