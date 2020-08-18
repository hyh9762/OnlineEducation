package com.itomelet.statistics.schedule;

import com.itomelet.statistics.service.DailyService;
import com.itomelet.statistics.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class ScheduledTask {
    @Resource
    private DailyService dailyService;

    //每天凌晨1点执行统计
    @Scheduled(cron = "0 0 1 * * ?")
    public void statisticsTask() {
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
