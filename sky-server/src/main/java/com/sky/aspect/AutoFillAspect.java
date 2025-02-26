package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Author: 韩栋林
 * @Description: 自定义切面类，实现公共字段自动填充
 * @DateTime: 2025/2/26 11:38
 **/
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    // 指定拦截哪些方法
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {}

    /**
     * 前置通知
     */
    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始进行公共字段自动填充");
        // 1. 获取被拦截方法的数据库操作类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); // 获取方法签名对象
        AutoFill autofill = methodSignature.getMethod().getAnnotation(AutoFill.class); // 获取方法的注解
        OperationType value = autofill.value(); // 注解对应的数据库操作类型

        // 2. 获取被拦截方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0]; // 约定实体对象放在第一位

        // 3. 准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // 4. 根据当前不同的操作类型，为对应的属性通过反射进行赋值
         if(value == OperationType.INSERT) {
             try{
                 // 为4个公共字段进行赋值
                 Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                 Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                 Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                 Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                 // 通过反射为对象属性赋值
                 setCreateTime.invoke(entity, now);
                 setCreateUser.invoke(entity, currentId);
                 setUpdateTime.invoke(entity, now);
                 setUpdateUser.invoke(entity, currentId);
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }else if (value == OperationType.UPDATE) {
             Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
             Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

             setUpdateTime. invoke(entity, now);
             setUpdateUser.invoke(entity, currentId);
         }

    }
}
