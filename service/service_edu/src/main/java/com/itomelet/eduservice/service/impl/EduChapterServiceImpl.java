package com.itomelet.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.eduservice.entity.EduChapter;
import com.itomelet.eduservice.entity.EduVideo;
import com.itomelet.eduservice.entity.vo.chapter.ChapterVo;
import com.itomelet.eduservice.entity.vo.chapter.VideoVo;
import com.itomelet.eduservice.mapper.EduChapterMapper;
import com.itomelet.eduservice.service.EduChapterService;
import com.itomelet.eduservice.service.EduVideoService;
import com.itomelet.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询所有章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduChapter::getCourseId, courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);
        List<ChapterVo> list = new ArrayList<>();
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            chapterVo.setVideos(getVideoByChapterId(eduChapter.getId()));
            list.add(chapterVo);
        }
        return list;
    }


    /**
     * 根据章节id查询小节
     *
     * @param id 章节id
     * @return 每个章节得小节集合
     */
    private List<VideoVo> getVideoByChapterId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduVideo::getChapterId, id);
        List<EduVideo> eduVideos = eduVideoService.list(wrapper);
        List<VideoVo> VideoVos = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            VideoVo videoVo = new VideoVo();
            videoVo.setId(eduVideo.getId());
            videoVo.setTitle(eduVideo.getTitle());
            VideoVos.add(videoVo);
        }
        return VideoVos;
    }

    /**
     * 删除章节的方法（如果章节下有小节则不能删除）
     *
     * @param chapterId 章节id
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterId查询小节表，如果有数据，不能删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduVideo::getChapterId, chapterId);
        int count = eduVideoService.count(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "该章节下还有小节，不能删除");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }

    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EduChapter::getCourseId, courseId);
        baseMapper.delete(wrapper);
    }
}
