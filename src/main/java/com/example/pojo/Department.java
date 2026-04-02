package com.example.pojo;

import java.util.List;

/**
 * 部门实体类
 * 用于演示一对多映射关系（一个部门有多个用户）
 */
public class Department {
    private Integer deptId;   // 部门ID
    private String deptName;  // 部门名称

    /**
     * ============================================
     * 一对多映射关系：一个部门有多个用户
     * 方式1：使用 List 集合存储用户列表
     * ============================================
     */
    private List<User> users; // 用户列表（一对多）

    // 无参构造
    public Department() {}

    // 有参构造
    public Department(Integer deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    // Getter & Setter
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取用户列表（一对多关系）
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * 设置用户列表（一对多关系）
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", users=" + users +
                '}';
    }
}
