package com.itomelet.order.controller;


import com.itomelet.commonutils.JwtUtils;
import com.itomelet.commonutils.Result;
import com.itomelet.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Resource
    private OrderService orderService;

    //1.生成订单方法
    @PostMapping("/createOrder/{courseId}")
    public Result createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单，返回订单号
        String orderNo = orderService.createOrder(courseId, memberId);
        return Result.success().data("orderId", orderNo);
    }
}

