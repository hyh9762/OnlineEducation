package com.itomelet.ucenter.controller;


import com.itomelet.commonutils.JwtUtils;
import com.itomelet.commonutils.Result;
import com.itomelet.ucenter.entity.Member;
import com.itomelet.ucenter.entity.vo.RegisterVo;
import com.itomelet.ucenter.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @PostMapping("/register")
    public Result registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.success();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        Member member = memberService.getById(memberId);
        return Result.success().data("userInfo", member);
    }
}

