package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 韩栋林
 * @Description: 菜品口味表接口
 * @DateTime: 2025/2/27 15:45
 **/

@Mapper
public interface DishFlavorMapper {

    /**
     * 向口味表插入多条数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品id删除指定口味
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);

    List<DishFlavor> getFlavorsByDishId(Long dishId);
}
