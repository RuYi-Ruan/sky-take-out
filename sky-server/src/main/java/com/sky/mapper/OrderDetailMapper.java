package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 韩栋林
 * @Description: 订单明细表相关数据库操作
 * @DateTime: 2025/3/4 16:59
 **/
@Mapper
public interface OrderDetailMapper {

    // 向订单明细表中插入多条数据
    void insertBatch(List<OrderDetail> orderDetailList);

    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);
}
