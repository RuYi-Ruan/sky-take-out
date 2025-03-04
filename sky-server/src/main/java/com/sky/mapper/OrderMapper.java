package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 韩栋林
 * @Description: 订单表数据库操作方法
 * @DateTime: 2025/3/4 16:53
 **/
@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param orders
     */
    void insert(Orders orders);
}
