package com.itomelet.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.order.entity.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-17
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNatvie(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrdersStatus(Map<String, String> map);
}
