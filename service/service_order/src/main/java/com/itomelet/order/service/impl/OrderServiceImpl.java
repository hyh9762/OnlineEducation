package com.itomelet.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.commonutils.ordervo.CourseWebVo;
import com.itomelet.commonutils.ordervo.UcenterMember;
import com.itomelet.order.client.EduClient;
import com.itomelet.order.client.UcenterClient;
import com.itomelet.order.entity.Order;
import com.itomelet.order.mapper.OrderMapper;
import com.itomelet.order.service.OrderService;
import com.itomelet.order.utils.OrderNoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private EduClient eduClient;

    @Resource
    private UcenterClient ucenterClient;


    @Override
    public String createOrder(String courseId, String memberId) {
        UcenterMember userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        CourseWebVo courseInfoVo = eduClient.getCourseInfoVo(courseId);
        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoVo.getTitle());
        order.setCourseCover(courseInfoVo.getCover());
        order.setTeacherName(courseInfoVo.getTeacherName());
        order.setTotalFee(courseInfoVo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
