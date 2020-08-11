package com.itomelet.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.eduservice.entity.EduVideo;
import com.itomelet.eduservice.mapper.EduVideoMapper;
import com.itomelet.eduservice.service.EduVideoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //根据课程id删小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduVideo::getCourseId, courseId);
        baseMapper.delete(wrapper);
    }
}
