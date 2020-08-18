package com.itomelet.statistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.statistics.entity.Daily;

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
}
