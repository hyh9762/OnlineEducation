package com.itomelet.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.commonutils.Result;
import com.itomelet.statistics.client.UcenterClient;
import com.itomelet.statistics.entity.Daily;
import com.itomelet.statistics.mapper.DailyMapper;
import com.itomelet.statistics.service.DailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-08-18
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Resource
    UcenterClient ucenterClient;


    @Override
    public void registerCount(String day) {
        Result result = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) result.getData().get("countRegister");
        //添加记录之前先删除表相同日期的数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Daily::getDateCalculated, day);
        baseMapper.delete(wrapper);

        //把获取数据添加到数据库，统计分析表
        Daily daily = new Daily();
        daily.setRegisterNum(countRegister);
        daily.setDateCalculated(day);
        //模拟
        daily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        daily.setLoginNum(RandomUtils.nextInt(100, 200));
        daily.setCourseNum(RandomUtils.nextInt(100, 200));
        baseMapper.insert(daily);
    }
}
