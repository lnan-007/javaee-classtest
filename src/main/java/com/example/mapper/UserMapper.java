package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 方式1: 单个参数传递
     * 最简单的方式，MyBatis 自动将参数值绑定到 #{任意名称}
     */
    User selectUserByName(String username);

    /**
     * 方式2: 多个参数传递 (不使用 @Param)
     * MyBatis 默认使用 param1, param2... 或 arg0, arg1... 作为参数名
     * XML 中必须使用 #{param1} 或 #{arg0} 来引用
     */
    User checkLogin(String username, Integer password);

    /**
     * 方式3: Map 参数传递
     * 使用 HashMap 作为参数，XML 中使用 #{key} 引用 Map 中的值
     */
    User checkLoginByMap(HashMap<String, Object> map);

    /**
     * 方式4: POJO 对象参数传递
     * 使用 JavaBean 对象作为参数，XML 中使用 #{属性名} 引用
     */
    int insertUser(User user);

    /**
     * 获取自增主键: 插入用户并返回自增ID
     * 使用 useGeneratedKeys="true" keyProperty="id" keyColumn="id"
     * 插入后，User对象的id属性会被自动设置为数据库生成的自增ID
     */
    int insertUserAndGetId(User user);

    /**
     * 方式5: 多个参数传递 (使用 @Param 注解)
     * 使用 @Param 指定参数名，XML 中直接使用 #{参数名} 引用
     */
    User checkLoginByParam(@Param("username") String username, @Param("password") Integer password);

    /**
     * 查询所有用户
     */
    List<User> findAll();

    /**
     * 根据 ID 查询用户
     */
    User findById(Integer id);

    /**
     * 更新用户
     */
    int updateUser(User user);

    /**
     * 根据 ID 删除用户
     */
    int deleteById(Integer id);

    /**
     * 情况1: 查询返回单一数值
     * 例如：查询用户数量、查询最大年龄等
     */
    int countUsers();

    /**
     * 情况2: 查询返回数据库中一条数据（不封装在实体类中）
     * 例如：查询用户姓名
     */
    String findUserNameById(Integer id);

    /**
     * 情况3: 查询返回数据库中多条数据（不封装在实体类中）
     * 方法1: 返回 List<String> - 例如：查询所有用户姓名
     */
    List<String> findAllUserNames();

    /**
     * 情况3: 查询返回数据库中多条数据（不封装在实体类中）
     * 方法2: 返回 List<Map> - 例如：查询所有用户的 ID 和姓名
     */
    List<Map<String, Object>> findAllUserIdAndNames();

    /**
     * 单个实体类查询: 根据姓名精确查询
     * 返回单个User对象或null（当查询结果不存在时）
     */
    User findByName(String name);

    /**
     * 单个实体类查询: 根据年龄精确查询
     * 返回单个User对象或null（当查询结果不存在时）
     */
    User findByAge(Integer age);

    /**
     * 多个实体类查询: 根据年龄范围查询
     * 返回符合条件的User集合，当无查询结果时返回空集合而非null
     */
    List<User> findByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);

    /**
     * 多个实体类查询: 根据关键词模糊查询姓名
     * 返回符合条件的User集合，当无查询结果时返回空集合而非null
     */
    List<User> findByKeyword(String keyword);

    /**
     * 多个实体类查询: 查询年龄大于指定值的用户
     * 返回符合条件的User集合，当无查询结果时返回空集合而非null
     */
    List<User> findByAgeGreaterThan(@Param("age") Integer age);

    /**
     * 模糊查询: 根据姓名前缀查询
     * 返回符合条件的User集合，当无查询结果时返回空集合而非null
     */
    List<User> findByPrefix(String prefix);

    /**
     * 模糊查询: 根据姓名后缀查询
     * 返回符合条件的User集合，当无查询结果时返回空集合而非null
     */
    List<User> findBySuffix(String suffix);

    /**
     * 动态表名查询: 根据表名查询所有记录
     * 使用 ${tableName} 实现动态表名（注意SQL注入风险）
     */
    List<Map<String, Object>> findAllFromTable(@Param("tableName") String tableName);

    /**
     * 批量删除: 根据ID列表批量删除用户
     * 返回删除的记录数
     */
    int batchDeleteByIds(@Param("ids") List<Integer> ids);

    /**
     * 批量删除: 根据年龄范围批量删除用户
     * 返回删除的记录数
     */
    int batchDeleteByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);

    /**
     * ============================================
     * 多对一映射关系处理方式1：级联方式
     * ============================================
     * 根据用户ID查询用户及其部门信息
     * 使用级联属性赋值（dept.deptId, dept.deptName）
     *
     * @param id 用户ID
     * @return 包含部门信息的User对象
     */
    User getUserWithDeptById(@Param("id") Integer id);

    /**
     * ============================================
     * 多对一映射关系处理方式2：使用 association 标签
     * ============================================
     * 根据用户ID查询用户及其部门信息
     * 使用 <association> 标签配置多对一关系
     *
     * @param id 用户ID
     * @return 包含部门信息的User对象
     */
    User getUserWithDeptByIdAssociation(@Param("id") Integer id);

    /**
     * ============================================
     * 多对一映射关系处理方式3：分步查询
     * ============================================
     * 根据用户ID查询用户及其部门信息（分步查询）
     * 第1步：查询用户信息
     * 第2步：根据 dept_id 调用 DepartmentMapper.getDeptById 查询部门
     *
     * @param id 用户ID
     * @return 包含部门信息的User对象
     */
    User getUserWithDeptByStep(@Param("id") Integer id);

    /**
     * ============================================
     * 【步骤1】一对多分步查询：根据部门ID查询用户列表
     * ============================================
     * 这个方法将被 DepartmentMapper 中的一对多分步查询调用
     * 通过 namespace.id 方式引用：com.example.mapper.UserMapper.getUsersByDeptId
     *
     * @param deptId 部门ID
     * @return 该部门下的所有用户列表
     */
    List<User> getUsersByDeptId(@Param("deptId") Integer deptId);
}
