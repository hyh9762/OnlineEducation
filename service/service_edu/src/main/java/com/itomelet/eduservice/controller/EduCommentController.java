package com.itomelet.eduservice.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itomelet.commonutils.JwtUtils;
import com.itomelet.commonutils.Result;
import com.itomelet.eduservice.client.MemberClient;
import com.itomelet.eduservice.entity.EduComment;
import com.itomelet.eduservice.entity.vo.MemberVo;
import com.itomelet.eduservice.service.EduCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Resource
    private EduCommentService eduCommentService;

    @Resource
    private MemberClient memberClient;

    //1.分页显示课程所有评论
    @GetMapping("/listAllComments/{current}/{limit}")
    public Result listAllComments(String courseId, @PathVariable long current, @PathVariable long limit) {
        Page<EduComment> pageEduComment = new Page<>(current, limit);
        pageEduComment = eduCommentService.listAllCommentsByCourseId(courseId, pageEduComment);
        return Result.success().data("items", pageEduComment.getRecords()).data("total", pageEduComment.getTotal());
    }

    //2.添加评论
    @PostMapping("/addComment")
    public Result addComment(@RequestBody EduComment eduComment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Result result = memberClient.getMemberInfo(memberId);
        MemberVo memberVo = JSONObject.parseObject((JSON.toJSONString(result.getData().get("userInfo"))), MemberVo.class);
        eduCommentService.addComment(eduComment, memberVo);
        return Result.success();
    }
}

