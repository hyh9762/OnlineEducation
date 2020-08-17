package com.itomelet.order.client;

import com.itomelet.commonutils.ordervo.CourseWebVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {
    @GetMapping("/eduservice/course/getCourseInfo/{id}")
    CourseWebVo getCourseInfoVo(@PathVariable("id") String id);
}
