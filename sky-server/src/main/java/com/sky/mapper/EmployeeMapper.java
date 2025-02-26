package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    // 新增员工
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    // 分页查询
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    // 编辑员工信息
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    // 根据id获取员工信息
    Employee getById(Long id);
}
