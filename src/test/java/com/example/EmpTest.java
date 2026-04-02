package com.example;

import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpTest {

    private SqlSession sqlSession;
    private EmpMapper empMapper;

    @Before
    public void setUp() throws IOException {
        initDatabase();

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession(true);
        empMapper = sqlSession.getMapper(EmpMapper.class);
    }

    private void initDatabase() {
        String url = "jdbc:mysql://localhost:3306/test_db?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
        String username = "root";
        String password = "ln1234567890";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE IF EXISTS t_emp");

            stmt.executeUpdate("CREATE TABLE t_emp (" +
                    "emp_id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "emp_name VARCHAR(50), " +
                    "age INT, " +
                    "gender VARCHAR(10), " +
                    "email VARCHAR(100))");

            stmt.executeUpdate("INSERT INTO t_emp (emp_name, age, gender, email) VALUES " +
                    "('张三', 25, '男', 'zhangsan@qq.com'), " +
                    "('李四', 30, '女', 'lisi@qq.com'), " +
                    "('王五', 25, '男', 'wangwu@qq.com'), " +
                    "('赵六', 35, '女', 'zhaoliu@qq.com')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEmpByConditionWithName() {
        System.out.println("========== 测试if标签：只传姓名 ==========");
        Emp condition = new Emp();
        condition.setEmpName("张三");

        List<Emp> emps = empMapper.getEmpByCondition(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionWithAge() {
        System.out.println("========== 测试if标签：只传年龄 ==========");
        Emp condition = new Emp();
        condition.setAge(25);

        List<Emp> emps = empMapper.getEmpByCondition(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionWithNameAndAge() {
        System.out.println("========== 测试if标签：传姓名和年龄 ==========");
        Emp condition = new Emp();
        condition.setEmpName("张三");
        condition.setAge(25);

        List<Emp> emps = empMapper.getEmpByCondition(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionWithAll() {
        System.out.println("========== 测试if标签：传所有条件 ==========");
        Emp condition = new Emp();
        condition.setEmpName("张三");
        condition.setAge(25);
        condition.setGender("男");
        condition.setEmail("zhangsan@qq.com");

        List<Emp> emps = empMapper.getEmpByCondition(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionWithNoCondition() {
        System.out.println("========== 测试if标签：不传任何条件 ==========");
        Emp condition = new Emp();

        List<Emp> emps = empMapper.getEmpByCondition(condition);
        emps.forEach(System.out::println);
    }

    // ==================== where标签测试 ====================

    @Test
    public void testGetEmpByConditionWhereWithName() {
        System.out.println("========== 测试where标签：只传姓名 ==========");
        Emp condition = new Emp();
        condition.setEmpName("张三");

        List<Emp> emps = empMapper.getEmpByConditionWhere(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionWhereWithAge() {
        System.out.println("========== 测试where标签：只传年龄 ==========");
        Emp condition = new Emp();
        condition.setAge(25);

        List<Emp> emps = empMapper.getEmpByConditionWhere(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionWhereWithNameAndAge() {
        System.out.println("========== 测试where标签：传姓名和年龄 ==========");
        Emp condition = new Emp();
        condition.setEmpName("张三");
        condition.setAge(25);

        List<Emp> emps = empMapper.getEmpByConditionWhere(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionWhereWithNoCondition() {
        System.out.println("========== 测试where标签：不传任何条件 ==========");
        Emp condition = new Emp();

        List<Emp> emps = empMapper.getEmpByConditionWhere(condition);
        emps.forEach(System.out::println);
    }

    // ==================== trim标签测试 ====================

    @Test
    public void testGetEmpByConditionTrimWithName() {
        System.out.println("========== 测试trim标签：只传姓名 ==========");
        Emp condition = new Emp();
        condition.setEmpName("张三");

        List<Emp> emps = empMapper.getEmpByConditionTrim(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionTrimWithAge() {
        System.out.println("========== 测试trim标签：只传年龄 ==========");
        Emp condition = new Emp();
        condition.setAge(25);

        List<Emp> emps = empMapper.getEmpByConditionTrim(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionTrimWithNameAndAge() {
        System.out.println("========== 测试trim标签：传姓名和年龄 ==========");
        Emp condition = new Emp();
        condition.setEmpName("张三");
        condition.setAge(25);

        List<Emp> emps = empMapper.getEmpByConditionTrim(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByConditionTrimWithNoCondition() {
        System.out.println("========== 测试trim标签：不传任何条件 ==========");
        Emp condition = new Emp();

        List<Emp> emps = empMapper.getEmpByConditionTrim(condition);
        emps.forEach(System.out::println);
    }

    // ==================== choose/when/otherwise标签测试 ====================

    @Test
    public void testGetEmpByChooseWithName() {
        System.out.println("========== 测试choose标签：只传姓名（优先使用） ==========");
        Emp condition = new Emp();
        condition.setEmpName("张三");
        // 同时设置年龄，但choose只会选择第一个满足的条件
        condition.setAge(30);

        List<Emp> emps = empMapper.getEmpByChoose(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByChooseWithAge() {
        System.out.println("========== 测试choose标签：只传年龄（姓名空） ==========");
        Emp condition = new Emp();
        // empName为空，所以会走到第二个when
        condition.setAge(25);

        List<Emp> emps = empMapper.getEmpByChoose(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByChooseWithGender() {
        System.out.println("========== 测试choose标签：只传性别（姓名年龄都空） ==========");
        Emp condition = new Emp();
        // empName和age都为空，所以会走到第三个when
        condition.setGender("男");

        List<Emp> emps = empMapper.getEmpByChoose(condition);
        emps.forEach(System.out::println);
    }

    @Test
    public void testGetEmpByChooseWithNoCondition() {
        System.out.println("========== 测试choose标签：不传任何条件（走otherwise） ==========");
        Emp condition = new Emp();

        List<Emp> emps = empMapper.getEmpByChoose(condition);
        emps.forEach(System.out::println);
    }

    // ==================== foreach标签测试（批量添加） ====================

    @Test
    public void testInsertEmps() {
        System.out.println("========== 测试foreach标签：批量添加员工 ==========");

        // 准备批量添加的员工数据
        List<Emp> emps = new ArrayList<>();
        emps.add(new Emp(null, "小明", 22, "男", "xiaoming@qq.com"));
        emps.add(new Emp(null, "小红", 24, "女", "xiaohong@qq.com"));
        emps.add(new Emp(null, "小刚", 26, "男", "xiaogang@qq.com"));

        // 执行批量插入
        empMapper.insertEmps(emps);

        // 验证插入结果：查询所有员工
        System.out.println("========== 插入后查询所有员工 ==========");
        Emp condition = new Emp();
        List<Emp> allEmps = empMapper.getEmpByCondition(condition);
        allEmps.forEach(System.out::println);
    }

    // ==================== foreach标签测试（批量删除） ====================

    @Test
    public void testDeleteEmpsByIds() {
        System.out.println("========== 测试foreach标签：批量删除员工 ==========");

        // 先查询所有员工
        System.out.println("========== 删除前查询所有员工 ==========");
        Emp condition = new Emp();
        List<Emp> beforeEmps = empMapper.getEmpByCondition(condition);
        beforeEmps.forEach(System.out::println);

        // 准备要删除的员工ID列表
        List<Integer> empIds = new ArrayList<>();
        empIds.add(1); // 删除张三
        empIds.add(2); // 删除李四

        // 执行批量删除
        empMapper.deleteEmpsByIds(empIds);

        // 验证删除结果
        System.out.println("========== 删除后查询所有员工 ==========");
        List<Emp> afterEmps = empMapper.getEmpByCondition(condition);
        afterEmps.forEach(System.out::println);
    }

    @After
    public void tearDown() {
        sqlSession.close();
    }
}
