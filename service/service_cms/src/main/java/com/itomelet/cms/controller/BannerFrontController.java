package com.itomelet.cms.controller;


import com.itomelet.cms.entity.CrmBanner;
import com.itomelet.cms.service.CrmBannerService;
import com.itomelet.commonutils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-13
 */
@RestController
@CrossOrigin
@RequestMapping("/cms/bannerFront")
public class BannerFrontController {
    @Resource
    private CrmBannerService bannerService;

    //查询所有banner
    @GetMapping("/getAllBanner")
    public Result getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return Result.success().data("list", list);
    }
}

