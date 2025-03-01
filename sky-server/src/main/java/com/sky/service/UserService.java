package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.UserLoginVO;

/**
 * @Author: 韩栋林
 * @Description: 用户相关方法
 * @DateTime: 2025/3/1 16:04
 **/
public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
