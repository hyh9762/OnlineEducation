package com.itomelet.eduservice.client;

import com.itomelet.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //定义调用方法的路径
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{id}")
    Result removeAliyunVideo(@PathVariable("id") String id);

    //删除多个阿里云视频的方法
    @DeleteMapping("/eduvod/video/delete-batch")
    Result deleteBatch(@RequestParam("videoIdList") List<String> videoList);
}
