package com.itomelet.acl.controller;

import com.alibaba.fastjson.JSONObject;
import com.itomelet.acl.service.IndexService;
import com.itomelet.commonutils.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController {

    @Resource
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public Result info() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.success().data(userInfo);
    }

    /**
     * 获取菜单
     *
     * @return
     */
    @GetMapping("menu")
    public Result getMenu() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.success().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public Result logout() {
        return Result.success();
    }

}
