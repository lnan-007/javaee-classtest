package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {

    /**
     * 多条件查询 - 使用if标签
     * 根据员工姓名和年龄进行动态条件查询
     */
    List<Emp> getEmpByCondition(Emp emp);

    /**
     * 多条件查询 - 使用where标签
     * where标签会自动处理多余的and/or，无需写where 1=1
     */
    List<Emp> getEmpByConditionWhere(Emp emp);

    /**
     * 多条件查询 - 使用trim标签
     * trim标签是万能标签，可以自定义前缀、后缀，去除多余字符
     * 功能比where标签更灵活强大
     */
    List<Emp> getEmpByConditionTrim(Emp emp);

    /**
     * 多条件查询 - 使用choose/when/otherwise标签
     * 类似Java的switch-case，只执行第一个满足条件的when，否则执行otherwise
     * 适用于：只选择一个条件进行查询（优先级查询）
     */
    List<Emp> getEmpByChoose(Emp emp);

    /**
     * 批量添加员工 - 使用foreach标签
     * @Param("emps") 指定集合参数名，在XML中通过该名称引用
     */
    void insertEmps(@Param("emps") List<Emp> emps);

    /**
     * 批量删除员工 - 使用foreach标签（IN语句）
     * 通过ID列表批量删除
     */
    void deleteEmpsByIds(@Param("empIds") List<Integer> empIds);
}
