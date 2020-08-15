package com.itomelet.ucenter.controller;


import com.itomelet.commonutils.Result;
import com.itomelet.ucenter.entity.Member;
import com.itomelet.ucenter.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-15
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {
    @Resource
    private MemberService memberService;

    //登录
    @PostMapping("/login")
    public Result loginUser(@RequestBody Member member) {
        //调用service方法实现登录
        String token = memberService.login(member);
        return Result.success().data("token", token);
    }

    //注册
}

