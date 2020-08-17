package com.itomelet.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itomelet.commonutils.JwtUtils;
import com.itomelet.commonutils.Result;
import com.itomelet.order.entity.Order;
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
        return Result.success().data("orderNo", orderNo);
    }

    //2 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderNo}")
    public Result getOrderInfo(@PathVariable String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Order::getOrderNo, orderNo);
        Order order = orderService.getOne(wrapper);
        return Result.success().data("item", order);
    }


    //根据课程id和用户id查询订单表中订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Order::getCourseId, courseId);
        wrapper.lambda().eq(Order::getMemberId, memberId);
        wrapper.lambda().eq(Order::getStatus, 1);//支付状态 1代表已经支付
        int count = orderService.count(wrapper);
        //已经支付
        return count > 0;
    }

}

