package com.itomelet.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.itomelet.oss.service.OssService;
import com.itomelet.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //1.获取文件名称
            String fileName = file.getOriginalFilename();
            //为文件名称添加唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("_", "");
            fileName = uuid + fileName;

            //2.把文件按照日期进行分类
            //2020/07/01/01.jpg
            //获取当前日期
            String dataPath = new DateTime().toString("yyyy/MM/dd/");
            //拼接
            fileName = dataPath + fileName;

            //调用oss方法上传
            ossClient.putObject(bucketName, fileName, inputStream);
            //返回路径
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

    }
}
