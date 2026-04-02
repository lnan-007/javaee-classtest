package com.example.mapper;

import com.example.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * DepartmentMapper 接口
 * 用于演示多对一分步查询 - 被调用的查询方法
 * 用于演示一对多映射关系
 */
@Mapper
public interface DepartmentMapper {

    /**
     * ============================================
     * 【步骤1】分步查询：根据部门ID查询部门信息
     * ============================================
     * 这个方法将被 UserMapper 中的分步查询调用
     *
     * @param deptId 部门ID
     * @return 部门对象
     */
    Department getDeptById(@Param("deptId") Integer deptId);

    /**
     * ============================================
     * 一对多映射关系处理方式1：使用 collection 标签
     * ============================================
     * 根据部门ID查询部门及其所有用户信息
     * 使用 <collection> 标签配置一对多关系
     *
     * @param id 部门ID
     * @return 包含用户列表的Department对象
     */
    Department getDeptWithUsersById(@Param("id") Integer id);

    /**
     * ============================================
     * 一对多映射关系处理方式2：分步查询
     * ============================================
     * 根据部门ID查询部门及其所有用户信息（分步查询）
     * 第1步：查询部门信息
     * 第2步：根据 dept_id 调用 UserMapper.getUsersByDeptId 查询用户列表
     *
     * @param id 部门ID
     * @return 包含用户列表的Department对象
     */
    Department getDeptWithUsersByStep(@Param("id") Integer id);
}
