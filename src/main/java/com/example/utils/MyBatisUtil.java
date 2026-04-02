package com.example.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {

    // 定义 SqlSessionFactory，使用 static 修饰，保证全局唯一
    private static SqlSessionFactory sqlSessionFactory;

    // 静态代码块：在类加载时只执行一次，用于初始化工厂
    static {
        try {
            // 1. 读取 MyBatis 的核心配置文件
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);

            // 2. 构建 SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            // 如果初始化失败，抛出运行时异常，防止程序继续运行
            throw new RuntimeException("MyBatis 初始化失败！", e);
        }
    }

    // 提供一个公共的静态方法，用于获取 SqlSession
    public static SqlSession getSqlSession() {
        // openSession(true) 表示自动提交事务（适合查询）
        // openSession() 表示需要手动 commit（适合增删改）
        return sqlSessionFactory.openSession();
    }

    // 可选：提供一个关闭资源的静态方法，也可以在测试类的 @After 中直接关闭
    public static void closeSqlSession(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }
}