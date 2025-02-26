package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 韩栋林
 * @Description: 自定义注解,用于公共字段填充
 * @DateTime: 2025/2/26 11:34
 **/

@Retention(RetentionPolicy.RUNTIME)
// 指定注解的位置-加在方法上
@Target(ElementType.METHOD)
public @interface AutoFill {
    // 数据库操作类型--INSERT UPDATE
    OperationType value();
}
