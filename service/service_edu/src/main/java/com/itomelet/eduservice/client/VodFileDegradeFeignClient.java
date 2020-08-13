package com.itomelet.eduservice.client;

import com.itomelet.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 调用方法出错时，调用这个方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result removeAliyunVideo(String id) {
        return Result.error().message("删除视频出错");
    }

    @Override
    public Result deleteBatch(List<String> videoList) {
        return Result.error().message("删除多个视频出错");
    }
}
