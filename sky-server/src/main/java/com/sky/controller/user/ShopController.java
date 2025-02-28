package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 韩栋林
 * @Description: 店铺相关接口
 * @DateTime: 2025/2/28 16:55
 **/
@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String SHOP_STATUS_KEY = "SHOP_STATUS";
    /**
     * 获取店铺营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Long> getStatus() {
        Long shopStatus = (Long) redisTemplate.opsForValue().get(SHOP_STATUS_KEY);
        log.info("店铺营业状态为：{}", shopStatus == 1 ? "营业" : "打样");
        return Result.success(shopStatus);
    }

}
