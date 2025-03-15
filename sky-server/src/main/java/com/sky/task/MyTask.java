package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: 韩栋林
 * @Description: 自定义定时任务类
 * @DateTime: 2025/3/15 15:18
 **/
@Component
@Slf4j
public class MyTask {

    /**
     * 定时任务每5S执行一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void execute(){
        log.info("定时任务开始执行, {}", new Date());
    }
}
