package com.itomelet.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.commonutils.ordervo.UcenterMember;
import com.itomelet.eduservice.client.MemberClient;
import com.itomelet.eduservice.entity.EduComment;
import com.itomelet.eduservice.mapper.EduCommentMapper;
import com.itomelet.eduservice.service.EduCommentService;
import com.itomelet.servicebase.exception.GuliException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    @Resource
    private MemberClient memberClient;

    @Override
    public Page<EduComment> listAllCommentsByCourseId(String courseId, Page<EduComment> pageEduComment) {
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EduComment::getCourseId, courseId);
        return baseMapper.selectPage(pageEduComment, queryWrapper);
    }

    @Override
    public void addComment(EduComment eduComment, String memberId) {
        UcenterMember ucenterMember = memberClient.getMemberInfo(memberId);
        eduComment.setMemberId(ucenterMember.getId());
        eduComment.setAvatar(ucenterMember.getAvatar());
        eduComment.setNickname(ucenterMember.getNickname());
        int insert = baseMapper.insert(eduComment);
        if (insert <= 0) {
            throw new GuliException(20001, "插入失败");
        }

    }


}
