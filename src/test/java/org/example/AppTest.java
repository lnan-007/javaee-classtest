package org.example;

import com.example.mapper.DepartmentMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Department;
import com.example.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.utils.MyBatisUtil;

public class AppTest {

    private SqlSession sqlSession;
    private UserMapper userMapper;
    private DepartmentMapper departmentMapper;

    @Before
    public void setup() {
        sqlSession = MyBatisUtil.getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
        departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        // 初始化数据库表结构（使用下划线命名 user_name）
        initDatabase();
    }

    /**
     * 初始化数据库表结构
     * 使用下划线命名(user_name)来演示 mapUnderscoreToCamelCase 配置
     * 同时创建 department 表用于演示多对一映射
     */
    private void initDatabase() {
        try {
            // 使用 JDBC 的 Statement 执行 DDL
            java.sql.Connection conn = sqlSession.getConnection();
            java.sql.Statement stmt = conn.createStatement();
            
            // 删除旧表（先删除外键关联的表）
            stmt.executeUpdate("DROP TABLE IF EXISTS user");
            stmt.executeUpdate("DROP TABLE IF EXISTS department");
            
            // 创建部门表
            stmt.executeUpdate("CREATE TABLE department (" +
                    "dept_id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "dept_name VARCHAR(50) NOT NULL)");
            
            // 创建用户表（添加 dept_id 外键）
            stmt.executeUpdate("CREATE TABLE user (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "user_name VARCHAR(50) NOT NULL, " +
                    "age INT, " +
                    "dept_id INT, " +
                    "FOREIGN KEY (dept_id) REFERENCES department(dept_id))");
            
            // 插入部门数据
            stmt.executeUpdate("INSERT INTO department (dept_name) VALUES ('技术部')");
            stmt.executeUpdate("INSERT INTO department (dept_name) VALUES ('销售部')");
            stmt.executeUpdate("INSERT INTO department (dept_name) VALUES ('人事部')");
            
            // 插入用户数据（关联部门）
            stmt.executeUpdate("INSERT INTO user (user_name, age, dept_id) VALUES ('Zhang San', 25, 1)");
            stmt.executeUpdate("INSERT INTO user (user_name, age, dept_id) VALUES ('Li Si', 28, 1)");
            stmt.executeUpdate("INSERT INTO user (user_name, age, dept_id) VALUES ('Wang Wu', 22, 2)");
            stmt.executeUpdate("INSERT INTO user (user_name, age, dept_id) VALUES ('Liu Bei', 40, 1)");
            stmt.executeUpdate("INSERT INTO user (user_name, age, dept_id) VALUES ('Cao Cao', 42, 3)");
            
            stmt.close();
            sqlSession.commit();
            System.out.println("Database initialized with department and user tables");
        } catch (Exception e) {
            System.out.println("Database init error: " + e.getMessage());
        }
    }

    @After
    public void teardown() {
        MyBatisUtil.closeSqlSession(sqlSession);
    }

    /**
     * 方式1: 单个参数传递
     */
    @Test
    public void testSelectUserByName() {
        System.out.println("========== 方式1: 单个参数传递 ==========");
        
        User user = userMapper.selectUserByName("Liu Bei");
        System.out.println("Query user with name='Liu Bei': " + user);
    }

    /**
     * 方式2: 多个参数传递 (不使用 @Param)
     */
    @Test
    public void testCheckLogin() {
        System.out.println("========== 方式2: 多个参数 (不使用 @Param) ==========");
        
        User user = userMapper.checkLogin("Zhang San", 25);
        System.out.println("Login check (name='Zhang San', age=25): " + user);
    }

    /**
     * 方式3: Map 参数传递
     */
    @Test
    public void testCheckLoginByMap() {
        System.out.println("========== 方式3: Map 参数传递 ==========");
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", "Cao Cao");
        map.put("password", 42);
        
        User user = userMapper.checkLoginByMap(map);
        System.out.println("Map login check (username='Cao Cao', password=42): " + user);
    }

    /**
     * 方式4: POJO 对象参数传递
     */
    @Test
    public void testInsertUser() {
        System.out.println("========== 方式4: POJO 对象参数传递 ==========");
        
        User user1 = new User("Guan Yu", 45);
        User user2 = new User("Zhang Fei", 42);
        User user3 = new User("Zhuge Liang", 36);
        
        userMapper.insertUser(user1);
        userMapper.insertUser(user2);
        userMapper.insertUser(user3);
        
        sqlSession.commit();
        
        System.out.println("Batch insert users successful:");
        System.out.println("  Guan Yu (45 years old) - ID: " + user1.getId());
        System.out.println("  Zhang Fei (42 years old) - ID: " + user2.getId());
        System.out.println("  Zhuge Liang (36 years old) - ID: " + user3.getId());
    }

    /**
     * 方式5: 多个参数传递 (使用 @Param 注解)
     */
    @Test
    public void testCheckLoginByParam() {
        System.out.println("========== 方式5: @Param 注解参数传递 ==========");
        
        User user = userMapper.checkLoginByParam("Sun Quan", 38);
        System.out.println("@Param login check (username='Sun Quan', password=38): " + user);
    }

    /**
     * 查询所有用户
     */
    @Test
    public void testFindAll() {
        System.out.println("========== All Users ==========");
        
        List<User> users = userMapper.findAll();
        System.out.println("Total " + users.size() + " records:");
        for (User user : users) {
            System.out.println("  " + user);
        }
    }

    /**
     * 情况1: 查询返回单一数值
     * 例如：查询用户数量
     */
    @Test
    public void testCountUsers() {
        System.out.println("========== 情况1: 查询返回单一数值 ==========");
        
        int count = userMapper.countUsers();
        System.out.println("Total users count: " + count);
    }

    /**
     * 情况2: 查询返回数据库中一条数据（不封装在实体类中）
     * 例如：查询用户姓名
     */
    @Test
    public void testFindUserNameById() {
        System.out.println("========== 情况2: 查询返回一条数据（String） ==========");
        
        String userName = userMapper.findUserNameById(9);
        System.out.println("User name for ID=9: " + userName);
    }

    /**
     * 情况3: 查询返回数据库中多条数据（不封装在实体类中）
     * 方法1: 返回 List<String> - 例如：查询所有用户姓名
     */
    @Test
    public void testFindAllUserNames() {
        System.out.println("========== 情况3: 查询返回多条数据（List<String>） ==========");
        
        List<String> userNames = userMapper.findAllUserNames();
        System.out.println("All user names (" + userNames.size() + "):");
        for (String name : userNames) {
            System.out.println("  " + name);
        }
    }

    /**
     * 情况3: 查询返回数据库中多条数据（不封装在实体类中）
     * 方法2: 返回 List<Map> - 例如：查询所有用户的 ID 和姓名
     */
    @Test
    public void testFindAllUserIdAndNames() {
        System.out.println("========== 情况3: 查询返回多条数据（List<Map>） ==========");
        
        List<Map<String, Object>> userIdAndNames = userMapper.findAllUserIdAndNames();
        System.out.println("All user IDs and names (" + userIdAndNames.size() + "):");
        for (Map<String, Object> map : userIdAndNames) {
            System.out.println("  ID: " + map.get("id") + ", Name: " + map.get("name"));
        }
    }

    /**
     * 单个实体类查询: 根据姓名精确查询
     */
    @Test
    public void testFindByName() {
        System.out.println("========== 单个实体类查询: 根据姓名精确查询 ==========");
        
        User user = userMapper.findByName("Liu Bei");
        System.out.println("Find user by name='Liu Bei': " + user);
        
        User notFoundUser = userMapper.findByName("NotExists");
        System.out.println("Find user by name='NotExists': " + notFoundUser + " (returns null when not found)");
    }

    /**
     * 单个实体类查询: 根据年龄精确查询
     */
    @Test
    public void testFindByAge() {
        System.out.println("========== 单个实体类查询: 根据年龄精确查询 ==========");
        
        User user = userMapper.findByAge(25);
        System.out.println("Find user by age=25: " + user);
        
        User notFoundUser = userMapper.findByAge(100);
        System.out.println("Find user by age=100: " + notFoundUser + " (returns null when not found)");
    }

    /**
     * 多个实体类查询: 根据年龄范围查询
     */
    @Test
    public void testFindByAgeRange() {
        System.out.println("========== 多个实体类查询: 根据年龄范围查询 ==========");
        
        List<User> users = userMapper.findByAgeRange(30, 40);
        System.out.println("Find users with age between 30 and 40 (" + users.size() + " records):");
        for (User user : users) {
            System.out.println("  " + user);
        }
        
        List<User> emptyResult = userMapper.findByAgeRange(100, 200);
        System.out.println("Find users with age between 100 and 200 (" + emptyResult.size() + " records, returns empty list)");
    }

    /**
     * 多个实体类查询: 根据关键词模糊查询姓名
     */
    @Test
    public void testFindByKeyword() {
        System.out.println("========== 多个实体类查询: 根据关键词模糊查询姓名 ==========");
        
        List<User> users = userMapper.findByKeyword("an");
        System.out.println("Find users with name containing 'an' (" + users.size() + " records):");
        for (User user : users) {
            System.out.println("  " + user);
        }
        
        List<User> emptyResult = userMapper.findByKeyword("NotExists");
        System.out.println("Find users with name containing 'NotExists' (" + emptyResult.size() + " records, returns empty list)");
    }

    /**
     * 多个实体类查询: 查询年龄大于指定值的用户
     */
    @Test
    public void testFindByAgeGreaterThan() {
        System.out.println("========== 多个实体类查询: 查询年龄大于指定值的用户 ==========");
        
        List<User> users = userMapper.findByAgeGreaterThan(40);
        System.out.println("Find users with age > 40 (" + users.size() + " records):");
        for (User user : users) {
            System.out.println("  " + user);
        }
        
        List<User> emptyResult = userMapper.findByAgeGreaterThan(100);
        System.out.println("Find users with age > 100 (" + emptyResult.size() + " records, returns empty list)");
    }

    /**
     * ============================================
     * 多对一映射关系处理方式1：级联方式测试
     * ============================================
     * 测试通过级联属性赋值（dept.deptId, dept.deptName）获取用户及其部门信息
     */
    @Test
    public void testGetUserWithDeptById() {
        System.out.println("========== 多对一映射：级联方式 ==========");
        
        // 查询ID为1的用户（张三是技术部的）
        User user = userMapper.getUserWithDeptById(1);
        System.out.println("User with ID=1:");
        System.out.println("  User Info: " + user);
        if (user.getDept() != null) {
            System.out.println("  Department Info: " + user.getDept());
        }
        
        System.out.println();
        
        // 查询ID为5的用户（曹操是人事部的）
        User user2 = userMapper.getUserWithDeptById(5);
        System.out.println("User with ID=5:");
        System.out.println("  User Info: " + user2);
        if (user2.getDept() != null) {
            System.out.println("  Department Info: " + user2.getDept());
        }
    }

    /**
     * ============================================
     * 多对一映射关系处理方式2：association 标签测试
     * ============================================
     * 测试使用 <association> 标签配置多对一关系
     */
    @Test
    public void testGetUserWithDeptByIdAssociation() {
        System.out.println("========== 多对一映射：association 标签方式 ==========");
        
        // 查询ID为1的用户（张三是技术部的）
        User user = userMapper.getUserWithDeptByIdAssociation(1);
        System.out.println("User with ID=1 (association):");
        System.out.println("  User Info: " + user);
        if (user.getDept() != null) {
            System.out.println("  Department Info: " + user.getDept());
        }
        
        System.out.println();
        
        // 查询ID为3的用户（王五是销售部的）
        User user2 = userMapper.getUserWithDeptByIdAssociation(3);
        System.out.println("User with ID=3 (association):");
        System.out.println("  User Info: " + user2);
        if (user2.getDept() != null) {
            System.out.println("  Department Info: " + user2.getDept());
        }
    }

    /**
     * ============================================
     * 多对一映射关系处理方式3：分步查询测试
     * ============================================
     * 测试分步查询：
     * 第1步：查询用户信息
     * 第2步：根据 dept_id 调用 DepartmentMapper.getDeptById 查询部门
     */
    @Test
    public void testGetUserWithDeptByStep() {
        System.out.println("========== 多对一映射：分步查询方式 ==========");
        System.out.println("【分步查询说明】");
        System.out.println("  第1步：SELECT id, user_name, age, dept_id FROM user WHERE id = ?");
        System.out.println("  第2步：SELECT dept_id, dept_name FROM department WHERE dept_id = ?");
        System.out.println();

        // 查询ID为1的用户（张三是技术部的）
        User user = userMapper.getUserWithDeptByStep(1);
        System.out.println("User with ID=1 (step):");
        System.out.println("  User Info: " + user);
        if (user.getDept() != null) {
            System.out.println("  Department Info: " + user.getDept());
        }

        System.out.println();

        // 查询ID为5的用户（曹操是人事部的）
        User user2 = userMapper.getUserWithDeptByStep(5);
        System.out.println("User with ID=5 (step):");
        System.out.println("  User Info: " + user2);
        if (user2.getDept() != null) {
            System.out.println("  Department Info: " + user2.getDept());
        }
    }

    /**
     * ============================================
     * 一对多映射关系处理方式1：使用 collection 标签测试
     * ============================================
     * 测试使用 <collection> 标签配置一对多关系
     * 查询部门及其所有用户信息
     */
    @Test
    public void testGetDeptWithUsersById() {
        System.out.println("========== 一对多映射：collection 标签方式 ==========");
        System.out.println("【一对多查询说明】");
        System.out.println("  查询部门时同时获取该部门下的所有用户");
        System.out.println("  SQL: SELECT d.*, u.* FROM department d LEFT JOIN user u ON d.dept_id = u.dept_id WHERE d.dept_id = ?");
        System.out.println();

        // 查询ID为1的部门（技术部，有3个用户：张三、李四、刘备）
        Department dept = departmentMapper.getDeptWithUsersById(1);
        System.out.println("Department with ID=1:");
        System.out.println("  Department Info: " + dept);
        if (dept.getUsers() != null && !dept.getUsers().isEmpty()) {
            System.out.println("  Users in this department:");
            for (User user : dept.getUsers()) {
                System.out.println("    - " + user);
            }
        }

        System.out.println();

        // 查询ID为2的部门（销售部，有1个用户：王五）
        Department dept2 = departmentMapper.getDeptWithUsersById(2);
        System.out.println("Department with ID=2:");
        System.out.println("  Department Info: " + dept2);
        if (dept2.getUsers() != null && !dept2.getUsers().isEmpty()) {
            System.out.println("  Users in this department:");
            for (User user : dept2.getUsers()) {
                System.out.println("    - " + user);
            }
        }

        System.out.println();

        // 查询ID为3的部门（人事部，有1个用户：曹操）
        Department dept3 = departmentMapper.getDeptWithUsersById(3);
        System.out.println("Department with ID=3:");
        System.out.println("  Department Info: " + dept3);
        if (dept3.getUsers() != null && !dept3.getUsers().isEmpty()) {
            System.out.println("  Users in this department:");
            for (User user : dept3.getUsers()) {
                System.out.println("    - " + user);
            }
        }
    }

    /**
     * ============================================
     * 一对多映射关系处理方式2：分步查询测试
     * ============================================
     * 测试分步查询：
     * 第1步：查询部门信息
     * 第2步：根据 dept_id 调用 UserMapper.getUsersByDeptId 查询用户列表
     */
    @Test
    public void testGetDeptWithUsersByStep() {
        System.out.println("========== 一对多映射：分步查询方式 ==========");
        System.out.println("【分步查询说明】");
        System.out.println("  第1步：SELECT dept_id, dept_name FROM department WHERE dept_id = ?");
        System.out.println("  第2步：SELECT id, user_name, age FROM user WHERE dept_id = ?");
        System.out.println();

        // 查询ID为1的部门（技术部）
        Department dept = departmentMapper.getDeptWithUsersByStep(1);
        System.out.println("Department with ID=1 (step):");
        System.out.println("  Department Info: " + dept);
        if (dept.getUsers() != null && !dept.getUsers().isEmpty()) {
            System.out.println("  Users in this department:");
            for (User user : dept.getUsers()) {
                System.out.println("    - " + user);
            }
        }

        System.out.println();

        // 查询ID为2的部门（销售部）
        Department dept2 = departmentMapper.getDeptWithUsersByStep(2);
        System.out.println("Department with ID=2 (step):");
        System.out.println("  Department Info: " + dept2);
        if (dept2.getUsers() != null && !dept2.getUsers().isEmpty()) {
            System.out.println("  Users in this department:");
            for (User user : dept2.getUsers()) {
                System.out.println("    - " + user);
            }
        }
    }
}
