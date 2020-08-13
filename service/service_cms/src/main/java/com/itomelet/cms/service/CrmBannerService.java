package com.itomelet.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.cms.entity.CrmBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-13
 */
public interface CrmBannerService extends IService<CrmBanner> {


    List<CrmBanner> selectAllBanner();
}
