package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @Author: 韩栋林
 * @Description: 菜品相关方法
 * @DateTime: 2025/2/27 15:23
 **/

public interface DishService {

    void saveWithFlavor(DishDTO dishDto);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBacth(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateStatus(Long status, Long id);

    void updateWithFlavor(DishDTO dishDto);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    List<Dish> list(Long categoryId);
}
