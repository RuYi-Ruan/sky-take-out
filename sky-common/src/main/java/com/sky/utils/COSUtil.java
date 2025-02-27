package com.sky.utils;

import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.model.PutObjectRequest;
import com.sky.properties.COSProperties;
import lombok.Data;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: 韩栋林
 * @Description: 腾讯云上传工具类
 * @DateTime: 2025/2/26 15:54
 **/

@Data
@Slf4j
public class COSUtil {

    private String baseUrl;
    private String accessKey;
    private String secretKey;
    private String regionName;
    private String bucketName;
    private String folderPrefix;

    public COSUtil(COSProperties cosProperties) {
        this.baseUrl = cosProperties.getBaseUrl();
        this.accessKey = cosProperties.getAccessKey();
        this.secretKey = cosProperties.getSecretKey();
        this.regionName = cosProperties.getRegionName();
        this.bucketName = cosProperties.getBucketName();
        this.folderPrefix = cosProperties.getFolderPrefix();
    }

    /**
     * 上传文件到腾讯云 COS（Cloud Object Storage）
     *
     * @param bytes     文件的字节数组
     * @param objectName 文件在 COS 中的目标存储路径（不包含前缀）
     * @return 返回文件的访问 URL
     * @throws IOException 如果上传过程中发生 I/O 错误，则抛出此异常
     */
    public String upload(byte[] bytes, String objectName) throws IOException {
        // 将字节数组转换为输入流，用于上传文件
        InputStream inputStream = new ByteArrayInputStream(bytes);

        // 创建 COS 的认证信息对象，使用 AccessKey 和 SecretKey 进行身份验证
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);

        // 配置客户端区域信息，指定目标 Bucket 所在的地域
        ClientConfig clientConfig = new ClientConfig(new Region(regionName));

        // 初始化 COS 客户端实例，用于执行与 COS 的交互操作
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            // 构建上传请求对象，指定目标 Bucket、文件路径、输入流等参数
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderPrefix + objectName, inputStream, null);

            // 执行文件上传操作，并获取上传结果
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            // 构造文件的访问 URL，结合基础域名、文件夹前缀和文件名生成完整路径
            String fileUrl = "https://" + baseUrl + folderPrefix + objectName;

            // 记录日志，输出文件的访问路径以便调试或跟踪
            log.info("文件路径：{}", fileUrl);

            // 返回文件的访问 URL，供调用方使用
            return fileUrl;
        } catch (Exception e) {
            // 捕获异常，打印堆栈信息以便排查问题
            e.printStackTrace();

            // 抛出自定义异常，提示上传失败
            throw new IOException("上传文件失败", e);
        } finally {
            // 确保关闭输入流，避免资源泄漏
            IOUtils.closeQuietly(inputStream);

            // 关闭 COS 客户端连接，释放相关资源
            cosClient.shutdown();
        }
    }
}
