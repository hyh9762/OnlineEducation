package com.itomelet.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itomelet.cms.entity.CrmBanner;
import com.itomelet.cms.service.CrmBannerService;
import com.itomelet.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author ekko
 * @since 2020-08-13
 */
@RestController
@RequestMapping("/cms/bannerAdmin")
public class BannerAdminController {

    @Resource
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "分页列出Banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        crmBannerService.page(pageBanner);
        return Result.success().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    @ApiOperation(value = "添加Banner")
    @PostMapping("/addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return Result.success();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return Result.success().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public Result updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return Result.success();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return Result.success();
    }
}

