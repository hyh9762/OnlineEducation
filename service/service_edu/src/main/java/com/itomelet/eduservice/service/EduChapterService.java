package com.itomelet.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.eduservice.entity.EduChapter;
import com.itomelet.eduservice.entity.vo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-01
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
