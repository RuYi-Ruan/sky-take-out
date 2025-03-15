package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: 韩栋林
 * @Description: 订单定时任务类
 * @DateTime: 2025/3/15 15:32
 **/
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时未支付订单 -- 每分钟触发一次
     */
    @Scheduled(cron = "0 * * * * ? ")
    public void processTimeOutOrder() {
        log.info("定时处理超时订单：{}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        // 获取超时未支付订单 select * from order where status = ? and order_time < ( cur_time - 15 )
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);

        // 如果不为空，则更定订单列表中的超时订单
        if(ordersList.size() > 0 && ordersList != null) {
            for (Orders order : ordersList) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时， 自动取消");
                order.setCancelTime(LocalDateTime.now());

                orderMapper.update(order);
            }
        }
    }

    /**
     * 处理一直处于派送状态的订单 -- 每天凌晨一点触发
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void processDeliverOrder() {
        log.info("定时处理处于派送中的订单：{}", LocalDateTime.now());

        // 只需处理上一天仍处于派送的订单
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);

        if (ordersList != null && ordersList.size() > 0) {
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }
    }
}

