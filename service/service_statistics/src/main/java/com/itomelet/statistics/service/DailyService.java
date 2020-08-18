package com.itomelet.statistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.statistics.entity.Daily;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-18
 */
public interface DailyService extends IService<Daily> {

    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
