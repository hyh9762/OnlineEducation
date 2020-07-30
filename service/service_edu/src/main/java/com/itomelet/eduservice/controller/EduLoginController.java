package com.itomelet.eduservice.controller;

import com.itomelet.commonutils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eduService/user")
@CrossOrigin//解决跨域问题
public class EduLoginController {

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login() {
        return Result.success().data("token", "admin");
    }

    /**
     * info
     */
    @PostMapping("/info")
    public Result info() {
        return Result.success().data("data", "[admin]").data("name", "admin").data("avatar", "图片地址");
    }

}
