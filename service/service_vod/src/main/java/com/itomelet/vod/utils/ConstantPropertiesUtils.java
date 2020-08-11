package com.itomelet.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    //读取配置文件内容
    @Value("${aliyun.vod.file.keyid}")
    private String keyId;
    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    //定义空开常量
    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() {
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
    }
}
