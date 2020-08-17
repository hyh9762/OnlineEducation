package com.itomelet.eduservice.client;

import com.itomelet.commonutils.ordervo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter")
@Component
public interface MemberClient {
    //根据id获取用户信息
    @GetMapping("/ucenter/member/getUserInfo/{id}")
    UcenterMember getMemberInfo(@PathVariable("id") String id);
}
