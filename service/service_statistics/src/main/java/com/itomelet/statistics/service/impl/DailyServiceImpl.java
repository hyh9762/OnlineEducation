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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //图表显示，返回两部分数据，日期json数组，数量json数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        List<Daily> dailyList = baseMapper.selectList(wrapper);

        //因为返回有两部分数据：日期 和 日期对应数量
        //前端要求数组json结构，对应后端java代码是list集合
        //创建两个list集合，一个日期list，一个数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        //遍历查询所有数据list集合，进行封装
        for (Daily daily : dailyList) {
            //封装日期list集合
            date_calculatedList.add(daily.getDateCalculated());
            //封装对应数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        //把封装之后两个list集合放到map集合，进行返回
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);
        return map;
    }
}
