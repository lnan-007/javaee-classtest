package com.example.pojo;

/**
 * 用户实体类
 * 演示多对一映射关系：多个 User 属于一个 Department
 */
public class User {
    private Integer id;
    private String userName; // 数据库字段是 user_name，实体类属性是 userName
    private Integer age;     // 数据库字段是 age，实体类属性是 age

    /**
     * ============================================
     * 多对一映射关系：多个用户属于一个部门
     * 方式1：级联方式 - 直接在 User 中定义 Department 对象
     * ============================================
     */
    private Department dept; // 部门对象（多对一）

    // 无参构造
    public User() {}

    // 有参构造
    public User(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }

    // Getter & Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取部门对象（多对一关系）
     */
    public Department getDept() {
        return dept;
    }

    /**
     * 设置部门对象（多对一关系）
     */
    public void setDept(Department dept) {
        this.dept = dept;
    }

    // toString 方法，方便打印对象
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", dept=" + dept +
                '}';
    }
}
