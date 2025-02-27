package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.properties.COSProperties;
import com.sky.utils.AliOssUtil;
import com.sky.utils.COSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 韩栋林
 * @Description: 配置类，用于创建AliOssUtil对象
 * @DateTime: 2025/2/26 14:38
 **/
@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean  // 确保工具类只创建一次
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云文件上传工具类对象：{}", aliOssProperties);

        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }

    @Bean
    @ConditionalOnMissingBean  // 确保工具类只创建一次
    public COSUtil cosUtil(COSProperties cosProperties) {
        log.info("开始创建腾讯云文件上传工具类对象：{}", cosProperties);

        return new COSUtil(cosProperties);
    }
}
