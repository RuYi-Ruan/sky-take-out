package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 韩栋林
 * @Description: 腾讯云配置属性
 * @DateTime: 2025/2/26 15:53
 **/
@Data
@Component
@ConfigurationProperties(prefix = "sky.cos")
public class COSProperties {

    private String baseUrl;
    private String accessKey;
    private String secretKey;
    private String regionName;
    private String bucketName;
    private String folderPrefix;

}
