package com.itomelet.order.client;

import com.itomelet.commonutils.ordervo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-ucenter")
@Component
public interface UcenterClient {
    //根据用户id获取用户信息(模块间传递)
    @PostMapping("/ucenter/member/getUserInfo/{id}")
    UcenterMember getUserInfoOrder(@PathVariable("id") String id);
}
