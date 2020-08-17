package com.itomelet.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.eduservice.entity.EduComment;
import com.itomelet.eduservice.entity.vo.MemberVo;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-17
 */
public interface EduCommentService extends IService<EduComment> {

    Page<EduComment> listAllCommentsByCourseId(String courseId, Page<EduComment> pageEduComment);

    void addComment(EduComment eduComment, MemberVo memberVo);
}
