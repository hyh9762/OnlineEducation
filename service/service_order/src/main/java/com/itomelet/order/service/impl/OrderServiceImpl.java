package com.itomelet.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.order.entity.Order;
import com.itomelet.order.mapper.OrderMapper;
import com.itomelet.order.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-08-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public String createOrder(String courseId, String memberId) {
        return null;
    }
}
