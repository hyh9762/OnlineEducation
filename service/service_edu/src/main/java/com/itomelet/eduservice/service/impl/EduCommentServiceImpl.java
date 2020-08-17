package com.itomelet.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.eduservice.entity.EduComment;
import com.itomelet.eduservice.entity.vo.MemberVo;
import com.itomelet.eduservice.mapper.EduCommentMapper;
import com.itomelet.eduservice.service.EduCommentService;
import com.itomelet.servicebase.exception.GuliException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-08-17
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Page<EduComment> listAllCommentsByCourseId(String courseId, Page<EduComment> pageEduComment) {
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EduComment::getCourseId, courseId);
        return baseMapper.selectPage(pageEduComment, queryWrapper);
    }

    @Override
    public void addComment(EduComment eduComment, MemberVo member) {
        eduComment.setMemberId(member.getId());
        eduComment.setAvatar(member.getAvatar());
        eduComment.setNickname(member.getNickname());
        int insert = baseMapper.insert(eduComment);
        if (insert <= 0) {
            throw new GuliException(20001, "插入失败");
        }

    }


}
