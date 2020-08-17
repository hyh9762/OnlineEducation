package com.itomelet.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.order.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-17
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);
}
