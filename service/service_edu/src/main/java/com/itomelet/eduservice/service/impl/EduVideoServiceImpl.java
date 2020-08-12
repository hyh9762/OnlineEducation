package com.itomelet.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.eduservice.client.VodClient;
import com.itomelet.eduservice.entity.EduVideo;
import com.itomelet.eduservice.mapper.EduVideoMapper;
import com.itomelet.eduservice.service.EduVideoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private VodClient vodClient;

    //根据课程id删小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        //1.删除小节之前删除视频
        //1.1根据课程id查出所有小节id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.lambda().eq(EduVideo::getCourseId, courseId);
        wrapperVideo.lambda().select(EduVideo::getVideoSourceId);
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }
        //集合不为空就删除
        if (!videoIds.isEmpty()) {
            vodClient.deleteBatch(videoIds);
        }

        //2.删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduVideo::getCourseId, courseId);
        baseMapper.delete(wrapper);
    }
}
