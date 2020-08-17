package com.itomelet.order.service.impl;

import com.itomelet.order.entity.PayLog;
import com.itomelet.order.mapper.PayLogMapper;
import com.itomelet.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-08-17
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
