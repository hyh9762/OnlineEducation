package com.itomelet.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.eduservice.entity.EduVideo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);
}
